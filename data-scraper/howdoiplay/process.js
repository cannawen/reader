const fs = require('fs');
const path = require('path');
const yaml = require('js-yaml');

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
  const finalString = "<speak version=\"1.0\">" + info.heroName + " Tips<break time=\"1s\"></break>" + info.tips.join("<break time=\"3s\"></break>") + "<break time=\"3s\"></break></speak>";
  fs.writeFileSync("data/" + info.heroName + ".txt", finalString, "utf8")
}

processHeros();

