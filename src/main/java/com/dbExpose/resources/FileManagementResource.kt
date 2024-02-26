package com.dbExpose.resources

import com.dbExpose.services.FileManagementService
import com.dbExpose.services.StudentManagementService
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import kotlin.jvm.Throws

@Path("/File")
class FileManagementResource @Inject constructor(
    private val fileManagementService: FileManagementService,
    private val studentManagementService: StudentManagementService
){

    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Throws(Exception::class)
    fun uploadAndDisplayContent(
        @FormDataParam("file") fileInputStream: InputStream,
        @FormDataParam("file") contentDispositionHeader: FormDataContentDisposition
    ) : String {
        try{
            val fileName=contentDispositionHeader.fileName
            val result=fileManagementService.saveFile(fileInputStream,fileName)
//            studentManagementService.bulkCreate(result)
            return "Successfully upload the content of the file: \n\n $result"
        }catch(e:Exception){
            val errorMessage = "Error uploading file: ${e.message}"
            return errorMessage
        }
    }

    @GET
    @Path("/downloadFile")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    @Throws(Exception::class)
    fun downloadFile(
        @QueryParam("filename") filename:String
    ):Response{
        val file=File("./$filename")
        if(!file.exists()){
            return Response.ok("error downloading file").build()
        }
        val responseBuilder = Response.ok(file.inputStream(), MediaType.APPLICATION_OCTET_STREAM)
        responseBuilder.header("Content-Disposition", "attachment; filename=\"${file.name}\"")
        val response = responseBuilder.build()

        return response
    }

    @GET
    @Path("/readFile/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    fun readFileContent(
        @PathParam("fileName") fileName:String
    ) : String{
        try{
            val result=fileManagementService.readContent(fileName)
            return result
        } catch(e:Exception){
            return "Error reading file:${e.message}"
        }
    }

    @GET
    @Path("/readDoc/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    fun readDocumentContent(
        @PathParam("fileName") fileName:String
    ) : String {
        try {
            val result=fileManagementService.readDocumentContent(fileName)
            return result
        } catch (e: Exception) {
            return "Error reading .docx file: ${e.message}"
        }
    }

//    @POST
//    @Path("/uploadDocument")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.MULTIPART_FORM_DATA)
//    fun uploadAndReadDocx(
//        @FormDataParam("file") inputStream: InputStream
//    ): String {
//        try {
//            val tempFile = File.createTempFile("uploadedFile", ".docx")
//
////            val saveFile=File.
//            FileOutputStream(tempFile).use { fileOutputStream ->
//                inputStream.copyTo(fileOutputStream)
//            }
//            val content = readDocxFile(tempFile)
//            tempFile.delete()
//            return content
//        } catch (e: Exception) {
//            return "Error uploading and reading .docx file: ${e.message}"
//        }
//    }
//
//    @POST
//    @Path("/uploadFile")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.TEXT_PLAIN)
//    @Throws(Exception::class)
//    fun displayContent(
//        @FormDataParam("file") fileInputStream: InputStream,
//        @FormDataParam("file") contentDispositionHeader: FormDataContentDisposition
//    ): Response {
//        val uploadDirectory="./"
//        try{
//            val fileName=contentDispositionHeader.fileName
//            println(fileName)
//            fileManagementService.saveFile(fileInputStream,uploadDirectory,fileName)
//
//            val result=readFileContent(fileName)
//            return Response.ok(result).build()
//        }catch(e:Exception){
//            val errorMessage = "Error uploading file: ${e.message}"
//            return Response.serverError().entity(errorMessage).build()
//        }
//    }
}