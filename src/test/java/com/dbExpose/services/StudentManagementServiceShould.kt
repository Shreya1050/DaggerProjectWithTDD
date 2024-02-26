package com.dbExpose.services

import com.dbExpose.DB_COLLECTION
import com.dbExpose.di.DatabaseModule
import com.dbExpose.helper.TestData
import com.dbExpose.models.StudentInfo
import com.dbExpose.repositories.StudentsManagementRepository
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
//import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.clearInvocations
import org.mockito.Mockito.mock
import com.nhaarman.mockito_kotlin.mock
import org.mockito.ArgumentMatchers.anyString
import org.mockito.stubbing.Answer
import kotlin.test.assertEquals
//import org.mockito.Mockito.*


class StudentManagementServiceShould {

    private val testData=TestData()
//    private val studentsManagementRepository=StudentsManagementRepository(testData.getDatabase(),"testCollection")
    private val studentsManagementRepository1 : StudentsManagementRepository = mock()
//private val studentsManagementRepository1=mock(StudentsManagementRepository::class.java)
    private var classUnderTest:StudentManagementService?=null


    @Before
    fun setup(){
        classUnderTest= StudentManagementService(studentsManagementRepository1)
    }

    @Test
    fun sampleTestCase(){
        assertEquals(true,true)
    }

    @Test
    fun getCountCase(){
        val output=testData.studentInfoList.size
        whenever(studentsManagementRepository1.getCount()).thenAnswer {
            testData.studentInfoList.size
        }
        assertEquals(output,classUnderTest!!.getCount())
    }

    @Test
    fun prepareResponseAsPerDataTrue(){
        val data=JSONObject().put("Sample","data")
        val status=500
        val message="failed"
        val output=JSONObject()
            .put("data",JSONObject().put("Sample","data"))
            .put("status",500)
            .put("error","failed")

        assertEquals(output,classUnderTest!!.prepareResponseAsPerData(data,status,message))
    }


    @Test
    fun deleteStudentCase(){
        val output=testData.removeStudent(uuid = "2")
        whenever(studentsManagementRepository1.deleteByName(anyString(),anyString())).thenAnswer {
            testData.removeStudent("2")
        }
        assertEquals(output.toString(),classUnderTest!!.deleteByName("alpha","beta"))
    }

    @Test
    fun createStudentCase1(){

        val inputStudent=testData.inputStudent(
            studentData = StudentInfo(
                uuid = "1",
                name = "Shreya",
                subject = "Maths",
                phNo = "9213113668",
                rollNo = 21,
                isVerified = true
            )
        )
        println(inputStudent)
        val outputStudent=testData.getStudentById("1")
        val inputStudentJson = Gson().toJson(inputStudent)
        println(outputStudent)
//        whenever(studentsManagementRepository1.createDoc2(any())).thenAnswer(
//            Answer { invocation -> testData.getStudentById((invocation.arguments[0] as StudentInfo?)?.uuid ?: "") }
//        )
        whenever(studentsManagementRepository1.createDoc2(StudentInfo())).thenAnswer(
            Answer { invocation -> testData.getStudentById((invocation.arguments[0] as StudentInfo).uuid ?: "") }
        )
        println(testData.getStudentById("1"))
        assertEquals(outputStudent,classUnderTest!!.createDoc2(inputStudentJson).toString())

////        val databaseMock = mock(DB_COLLECTION::class.java)
//        val studentData=JSONObject()
//            .put("name","Shreya")
//            .put("subject","Maths")
//            .put("uuid","1243")
//            .put("phNo","9089975523")
//
//        val output=studentsManagementRepository.createDoc(studentData)
//        println(output)
////        whenever(studentsManagementRepository.createDoc(any())).thenAnswer {
////            invocation ->
////        }
//        assertEquals(studentData.toString(),output)
    }

//    @Test
//    fun manageStudentCase2(){
//        studentsManagementRepository.getById("112")
//    }

    @After
    fun tearDown(){
        testData.closeDbConnection()
    }

}