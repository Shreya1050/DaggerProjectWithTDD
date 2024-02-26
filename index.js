const express = require('express')
const request = require('request')
//import express from "express"
const app = express()
const port = 3000

// const options={
//   hostname:'localhost:8000',
//   path:application/data,
//   method:post
// }

_EXTERNAL_URL = 'http://localhost:8000'

const req= http.request(_EXTERNAL_URL,(res)=>{
  let data='';

  res.on('data',(chunk)=>{
    data+=chunk;
  });
  res.on('end',() => {
    return callback(data);
    //console.log(JSON.parse(data));
    // Use the response data in your project
  });
}
)

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/about',(req,res) => {
    res.send('Email for any query')
})

// app.get('/infomation',(req, res) => {
//         // let response = await request.get('http://localhost:3000');
//         //   if (response.err) { console.log('error');}
//         //   else { console.log('fetched response');}
//         f1()
// })
// f1()
// async function f1(){
//     //throw new Error("err")
//     let res=await cSqr(2)
//     console.log(res)
// }

// function cSqr(req){
    
// }

// const fn=f1()
// console.log(f1)


app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})