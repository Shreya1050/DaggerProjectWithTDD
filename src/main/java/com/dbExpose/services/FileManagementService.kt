package com.dbExpose.services;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.inject.Inject
import javax.ws.rs.NotFoundException

class FileManagementService @Inject constructor() {

    private val uploadDirectory="./"

    fun saveFile(
        inputStream:InputStream, fileName: String
    ) : String{
        val outputFile = File(uploadDirectory, fileName)
        FileOutputStream(outputFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        val result:String
        if(getSuffix(fileName)=="docx"){
            result=readDocumentContent(fileName)
        }else if(getSuffix(fileName)=="csv"){
            val content=readFileContent(fileName)
            val jsonArray=convertCsvToJsonArray(content)
            println(jsonArray)
            result=jsonArray.toString()
        }
        else{
            result=readFileContent(fileName)
        }
        return result
    }

    private fun convertCsvToJsonArray(csvContent: String): JSONArray {
        val lines = csvContent.lines()
        println(lines.size)
        val keys = lines.first().split(",") // Get keys from the first row
        val jsonArray = JSONArray()

        for (i in 1 until lines.size-1) { // Iterate over subsequent rows
            val values = lines[i].split(",")
            val jsonObject = JSONObject()

            // Create JSON object with keys and values
            for (j in keys.indices) {
                jsonObject.put(keys[j], values[j])
            }
            jsonArray.put(jsonObject)
        }
        return jsonArray
    }

//    fun saveFile2(
//        inputStream:InputStream, fileName: String
//    ) : String {
//        val outputFile = File(uploadDirectory, fileName)
//        FileOutputStream(outputFile).use { outputStream ->
//            inputStream.copyTo(outputStream)
//        }
//        val result=readFileContent(fileName)
//        return result
//    }

    private fun getSuffix(fileName:String):String{
        val lastDotIndex=fileName.lastIndexOf('.')
        if(lastDotIndex>=0 && lastDotIndex<fileName.length-1){
            return fileName.substring(lastDotIndex+1)
        }
        else{
            return ""
        }
    }

    fun readContent(fileName: String):String{
        var result:String
        if (getSuffix(fileName)=="docx"){
            result=readDocumentContent(fileName)
        }
        else{
            result=readFileContent(fileName)
        }
        return result
    }
    fun readFileContent(
        fileName: String,
    ):String{
        val file= File(uploadDirectory,fileName)
        if(file.exists()){
            val fileContent= Files.readString(file.toPath(), StandardCharsets.UTF_8)
            return fileContent
        } else{
            throw NotFoundException("File not found:$fileName")
        }
    }

    fun readDocumentContent(
        fileName: String
    ):String {
        val file = File(uploadDirectory, fileName)
        if (file.exists()) {
            FileInputStream(file).use { fileInputStream ->
                val document = XWPFDocument(fileInputStream)
                val extractor = XWPFWordExtractor(document)
                val content = extractor.text
                document.close()
                extractor.close()
                return content
            }
        } else {
            throw NotFoundException("File not found:$fileName")
        }
    }
}