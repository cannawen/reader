const fs = require('fs');
const path = require('path');
const request = require('request');
const yaml = require('js-yaml');

function uploadAll() {
  fs.readdirSync("data").forEach(file => {
    const fileComponents = path.parse(file);
    if (fileComponents.ext === ".txt") {
      const ssml = fs.readFileSync("data/" + fileComponents.name + ".txt", "utf8");
      upload(fileComponents.name, ssml);
    }
  })
}

function upload(name, info) {
  request({'uri' : 'http://reader-server.cannawen.com/api/chapter',
           'method' : "POST",
           'json' : true,
           'body' : {"title" : name, "text" : info}
         })
  .on('response', function(response) {
    console.log(response.statusCode) // 200
  })
  .on('error', function(err) {
    console.log(err)
  });
}

uploadAll();
