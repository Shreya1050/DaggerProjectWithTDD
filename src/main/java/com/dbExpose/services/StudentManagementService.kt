package com.dbExpose.services

import com.dbExpose.models.StudentInfo
import com.dbExpose.repositories.StudentsManagementRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class StudentManagementService @Inject constructor(
    private val studentsManagementRepository: StudentsManagementRepository
){

    @Deprecated("replaced by createDoc2")
    fun createDoc(
        studentObj: String
    ):JSONObject{
        val response = JSONObject()
        val success = JSONArray()
        val failed = JSONArray()
        val jsonObj=JSONObject(studentObj)
        try{
            if(!jsonObj.has("uuid")){
                jsonObj.put("uuid", UUID.randomUUID().toString())
                jsonObj.put("_id",jsonObj.get("uuid"))
            }
            else{
                jsonObj.put("uuid",jsonObj.get("uuid"))
                jsonObj.put("_id",jsonObj.get("uuid"))
            }
            studentsManagementRepository.createDoc(jsonObj)
            response.put("success",success)
        }catch (e:Exception){
            response.put("failed", failed)
        }
        return response
    }

    fun createDoc2(
        studentData: String
    ):JSONObject{
        var response:JSONObject
        val success = "Your request has been processed successfully"
        val error="Error:Bad request or duplicate document"
        val failed = "Important fields missing!!:Please provide both name and phone number"
        val gson1 = Gson()
        val studentObj: StudentInfo = gson1.fromJson(studentData, StudentInfo::class.java)
        if(studentObj.phNo.isNullOrBlank() || studentObj.name.isNullOrBlank()){
            response = prepareResponseAsPerData(null,400,failed)
        }
        else{
            try{
                val jsonString=gson1.toJson(studentObj)
                val jsonObj=JSONObject(jsonString)

                if(!jsonObj.has("uuid")){
                    jsonObj.put("uuid", UUID.randomUUID().toString())
                    jsonObj.put("_id",jsonObj.get("uuid"))
                }
                else{
                    jsonObj.put("uuid",jsonObj.get("uuid"))
                    jsonObj.put("_id",jsonObj.get("uuid"))
                }
                studentsManagementRepository.createDoc2(studentObj)
                response=prepareResponseAsPerData(jsonObj,200,success)
            }catch (e:Exception){
                response = prepareResponseAsPerData(null,400,error)
            }
        }
        return response
    }

    fun bulkCreate(
        studentsData: String
    ):JSONArray{
        val response = JSONArray()
        val success = "Your request has been processed successfully"
        val error="Error:Bad request or duplicate document"
        val failed = "Important fields missing!!:Please provide both name and phone number"
        val gson=Gson()
        val studentList= object : TypeToken<List<StudentInfo>>() {}.type
        val students : List<StudentInfo> = gson.fromJson(studentsData, Array<StudentInfo>::class.java).toList()
//        val students: List<StudentInfo> = gson.fromJson(studentsData, studentList)
        students.forEach{studentObj->
//            println(studentObj)
            if(studentObj.phNo.isNullOrBlank() || studentObj.name.isNullOrBlank()){
                response.put(prepareResponseAsPerData(null,400,failed))
            }
            else{
                try{
                    //val gson2= Gson()
                    val jsonString=gson.toJson(studentObj)
                    val jsonObj=JSONObject(jsonString)

                    if(!jsonObj.has("uuid")){
                        jsonObj.put("uuid", UUID.randomUUID().toString())
                        jsonObj.put("_id",jsonObj.get("uuid"))
                    }
                    else{
                        jsonObj.put("uuid",jsonObj.get("uuid"))
                        jsonObj.put("_id",jsonObj.get("uuid"))
                    }
                    studentsManagementRepository.createDoc2(studentObj)
                    response.put(prepareResponseAsPerData(jsonObj,200,success))
                }catch (e:Exception){
                    response.put(prepareResponseAsPerData(null,400,e.message))
                }
            }
        }
        return response
    }

    fun prepareResponseAsPerData(
        data:JSONObject?,
        status:Int,
        error:String?):JSONObject{
        val json=JSONObject()
            .put("status",status)
            .put("error",error)
        if(data==null){
                json.put("data","null")
            }
        else{
            json.put("data",data)
            }
        return json
    }

    fun getById(
        uuid:String
    ):JSONObject{
        return studentsManagementRepository.getById(uuid)
    }

    fun getCount():Int{
        return studentsManagementRepository.getCount()
    }

    fun deleteByName(key:String,value:String):String{
        return studentsManagementRepository.deleteByName(key,value)
    }

    fun updateData(
        id:String,
        studentObj:JSONObject
    ):String{
        val checkPresence=studentsManagementRepository.getById(id)
        if(checkPresence.isEmpty){
            return "Failure:Document with uuid: $id not found"
        }
        else{
            studentsManagementRepository.updateData(id,studentObj)
            val updatedDoc=studentsManagementRepository.getById(id)
            return updatedDoc.toString()
        }
    }

}