const cheerio = require('cheerio')
const request = require('request');
const yaml = require('js-yaml');
const fs = require('fs');

function fetchAllHeros() {
  request('https://howdoiplay.com/', (error, response, html) => {
    if (!error && response.statusCode == 200) {
      const $ = cheerio.load(html);
      $('.herolist > li')
        .each((i, element) => {
          const hero = $(element).attr('class').split(' ')[1];
          fetchHeroPage('https://howdoiplay.com/tips/' + hero + ".html");
        })
        .get();
    }
  });
}

function fetchHeroPage(url) {
  request(url, (error, response, html) => {
    if (!error && response.statusCode == 200) {
      const $ = cheerio.load(html);
      
      const heroName = $('.name').text().trim();
      const tips = 
        $('ul > li')
          .map((i, element) => $(element).text().trim().replace(/\n/g, " "))
          .get();

      const info = {
        "heroName" : heroName,
        "tips" : tips,
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
