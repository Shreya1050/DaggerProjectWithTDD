package com.dbExpose.repositories

import com.dbExpose.DB_COLLECTION
import com.dbExpose.models.StudentInfo
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class StudentsManagementRepository @Inject constructor(
    database:MongoDatabase,
    @Named(DB_COLLECTION) collectionName:String
){
    private var collection: MongoCollection<Document>

    init{
        collection=database.getCollection(collectionName)
        collection.createIndex(BasicDBObject().append("orgId",1).append("type",1))
    }

    fun createDoc(studentObj:JSONObject):String{
        try{
            val jsonString=studentObj.toString()
            val document=Document.parse(jsonString)
            collection.insertOne(document)
            return studentObj.toString()
        }catch(e:Exception){
            return "error storing data"
        }
    }

    fun createDoc2(studentObj: StudentInfo) : String{
        val document=Document.parse(studentObj.toString())
        document.put("_id",studentObj.uuid)
        println(document)
        collection.insertOne(document)
        return studentObj.toString()
    }

    fun getById(uuid:String):JSONObject{
        val searchQuery=eq("_id",uuid)
        val cursor=collection.find(searchQuery).limit(1).iterator()
        val jsonObj=JSONObject()
        if(cursor.hasNext()){
            val doc=cursor.next()
            doc.remove("_id")
            val json = JSONObject(doc.toJson())
            return json
        }
        return jsonObj
    }

    fun getCount():Int{
        val cursor=collection.find().iterator()
        var count=0
        while(cursor.hasNext()){
            count++
            cursor.next()
        }
        return count
    }

//    fun update(key:String,value:String){
//        val updateQuery=eq(key,value)
//        val update=collection.updateOne(updateQuery,updateQuery)
//    }

    fun deleteByName(
        key: String,value: String
    ):String{
        val deleteQuery=eq(key,value)
        val cursor=collection.find(deleteQuery).iterator()
        if(cursor.hasNext()){
            collection.deleteOne(deleteQuery)
            return "Deletion successful"
        }
        else{
            return "No such document found"
        }
    }

    fun updateData(
        searchQ:String,updateQ:JSONObject
    ){
        val oldDoc=eq("_id",searchQ)
        val doc=Document.parse(updateQ.toString())
        val updateQuery=BasicDBObject("\$set",doc)
        collection.updateOne(oldDoc,updateQuery, UpdateOptions().upsert(true))
    }

//        fun getByName(name:String):JSONObject{
//        val searchQuery=eq("name",name)
//        val cursor=collection.find(searchQuery).limit(1).iterator()
//        val json=JSONObject()
//        if(cursor.hasNext()){
//            val doc=cursor.next()
//            doc.remove("_id")
//            return JSONObject(doc.toJson())
//        }
//        return json
//    }

}