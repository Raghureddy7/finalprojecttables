var express=require('express'); // express lib
const app=express();
var mysql=require('mysql');// mysql lib

var bodyparser=require('body-parser'); // json data parsing

//mysql connection
var connect=mysql.createConnection({
    host:'localhost',
    user:"root",
    password:"anurag",
    database:'shoponline'
});

connect.connect();// mysql connection test

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({extended:true}));


// products 

app.post('/addproduct/',(req,res,next)=>{

    var data=req.body;
    var product_name=data.product_name;
    var brand_name=data.brand_name;
    var product_price=data.product_price;
    var product_cat=data.product_cat;
    var gender=data.gender;
    var image_file=data.image_file;

// for inserting data

var insert_cmd="insert into products (product_name,brand_name,product_price,product_cat,gender,image_file) values (?,?,?,?,?,?)";
values=[product_name,brand_name,product_price,product_cat,gender,image_file];
connect.query(insert_cmd,values,(err,results,fields)=>{

    connect.on('error',(err)=>{
        console.log("[MySQL Error",err);
    });

    console.log("Inserted");
});

connect.query("select * from products where product_name=? order by id desc limit 1",[product_name], function(err,result,fields){
    connect.on('error',(err)=>{
        console.log("[MySQL Error",err);
    });
    
    if(result && result.length){
        res.json(result[0]);
    }
});          
        

    });

app.post('/viewproduct/',(req,res,next)=>{

    var data=req.body;
            connect.query("select * from products",[], function(err,result,fields){
                connect.on('error',(err)=>{
                    console.log("[MySQL Error",err);
                });
                
                if(result && result.length){
                    res.json(result);
                }
            });     
});



var server=app.listen(3060,()=>{   
    console.log("Server running at http://localhost:3060");
});


