package com.dbExpose.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class StudentInfo(
    var _id:String?=null,
    var uuid:String?=null,
    var name:String?=null,
    var rollNo:Int?=null,
    var subject:String?=null,
    var phNo:String?=null,
    var isVerified:Boolean?=null
){

    constructor():this(null,null,null,null,null,null,null)

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }

    fun validateCorrectData(){
        this.uuid!=null
    }
}

data class StudentInfo2(
    var _id: String?=null
){

}

