package com.dbExpose.helper

import com.dbExpose.models.StudentInfo
import com.google.gson.Gson
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.distribution.Version
import org.bson.Document
import org.json.JSONObject

class TestData {
//    var collection: MongoCollection<Document>
    var mongoClient: MongoClient
    val studentInfoList:MutableList<StudentInfo> = mutableListOf()
    val gson= Gson()


    init{
        mongoClient = MongoClients.create("mongodb://localhost:27017/mydatabase")
//        val database = mongoClient.getDatabase("test")
//        collection=database.getCollection("testCollection")
//        collection.createIndex(BasicDBObject().append("orgId",1).append("type",1))
    }

    fun getDatabase():MongoDatabase{
        return mongoClient.getDatabase("test")
    }

    fun closeDbConnection(){
        mongoClient.close()
    }

    fun inputStudent(studentData:StudentInfo):String{
//        val studentData=StudentInfo()
//        val studentObj=gson.fromJson(studentData.toString(),StudentInfo::class.java)
        studentInfoList.add(studentData)
        println(studentInfoList)
        return studentData.toString()
    }

    fun getStudentById(uuid:String):String{
        var result:String="No student found with the given uuid"
        for(student in studentInfoList){
            if(student.uuid==uuid){
                result=gson.toJson(student)
                break
            }
        }
        return result
    }

    fun removeStudent(uuid:String):Int{
        studentInfoList.remove(Gson().fromJson(getStudentById(uuid),StudentInfo::class.java))
        return studentInfoList.size
    }

    fun studentDatabase():MutableList<StudentInfo>{
        return studentInfoList
    }

}