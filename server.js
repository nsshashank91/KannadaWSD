var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var fs = require('fs');
var encoding = require('encoding');
//app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.post('/', function(req, res) {
  console.log("request received");
  res.setHeader('Access-Control-Allow-Origin','*');
});

app.post('/send_save', function(req, res) {
    var txt = req.body.data;
    //fs.writeFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt','');
	fs.writeFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt','');
    txt.forEach(function(value){
        console.log(value);
        /*fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',value.posWord);
        fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',"::::::");
        fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',value.synWords);
        fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',"::::::");
        fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',value.gloss);
		fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',"::::::");
		fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',value.example);
        fs.appendFileSync('C:\\Users\\shashank\\workspaceKannada\\KannadaWSD\\message.txt',"----------------------------");*/
		
		 fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',value.posWord);
        fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',"::::::");
        fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',value.synWords);
        fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',"::::::");
        fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',value.gloss);
		fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',"::::::");
		fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',value.example);
        fs.appendFileSync('C:\\Users\\shashank\\Documents\\wsd\\wsd\\src\\main\\resources\\message.txt',"----------------------------");

    });
    

    

});

app.listen(3000);
console.log("server running on 3000");