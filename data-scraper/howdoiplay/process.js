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
  const finalString = info.heroName + " Tips \n\n" + info.tips.join('\n\n');
  fs.writeFileSync("data/" + info.heroName + ".txt", finalString, "utf8")
}

processHeros();

