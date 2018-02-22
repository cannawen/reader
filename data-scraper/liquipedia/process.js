const yaml = require('js-yaml');
const fs = require('fs');
const path = require('path');

function processHeros() {
  fs.readdirSync("raw-data").forEach(file => {
    const fileComponents = path.parse(file);
    if (fileComponents.ext === ".yml") {
      const hero = yaml.safeLoad(fs.readFileSync("raw-data/" + fileComponents.name + ".yml", "utf8"));
      processHero(hero);
    }
  })
}

function processHero(info) {
  const finalString = [
    info.fullHeroName,
    info.heroClass + " Hero",
    info.description.join('\n'),
    info.abilityNames.reduce((memo, element, index) => {
      memo.push(element);
      memo.push(info.abilityDescriptions[index]);
      return memo;
    }, []).join('.\n')
  ].join('.\n\n');
  fs.writeFileSync("data/" + info.heroName + ".txt", finalString, "utf8")
}

processHeros();

