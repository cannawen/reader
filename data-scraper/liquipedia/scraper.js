const cheerio = require('cheerio')
const request = require('request');
const yaml = require('js-yaml');
const fs = require('fs');

function fetchAllHeros() {
  request('http://liquipedia.net/dota2/Portal:Heroes', (error, response, html) => {
    if (!error && response.statusCode == 200) {
      const $ = cheerio.load(html);
      $('.halfbox')
        .each((i, element) => {
          const heros = $(element).find('li > a');
          
          let heroClass;
          if (i == 0) {
            heroClass = "Strength";
          } else if (i == 1) {
            heroClass = "Agility";
          } else {
            heroClass = "Intelligence";
          }

          heros.each((i, element) => fetchHeroPage('http://liquipedia.net' + $(element).attr('href'), heroClass))
        })
        .get();
    }
  });
}

function fetchHeroPage(url, heroClass) {
  request(url, (error, response, html) => {
    if (!error && response.statusCode == 200) {
      const $ = cheerio.load(html);
      
      const heroName = $('.firstHeading > span').text().trim();
      const fullHeroName = $('.mw-headline').eq(0).text().trim();
      const description = 
        $('#mw-content-text > table')
          .eq(0)
          .nextUntil('h2')
          .map((i, element) => $(element).text().trim())
          .get();
      const abilityNames = 
        $('#mw-content-text .spellcard-name')
          .map((i, element) => $(element).text().trim())
          .get();
      const abilityDescriptions =
        $('#mw-content-text .spellcard-description')
          .map((i, element) => $(element).text().trim())
          .get();

      const info = {
        "heroName" : heroName,
        "fullHeroName" : fullHeroName,
        "description" : description,
        "abilityNames" : abilityNames,
        "abilityDescriptions" : abilityDescriptions,
        "heroClass" : heroClass,
        "source" : url
      }

      saveHeroInfo(info);
    }
  });
}

function saveHeroInfo(info) {
  fs.writeFileSync("raw-data/" + info.heroName + ".yml", yaml.safeDump(info), "utf8");
}

fetchAllHeros();
