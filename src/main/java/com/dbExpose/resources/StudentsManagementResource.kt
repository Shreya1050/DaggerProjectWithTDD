package com.dbExpose.resources

import com.dbExpose.services.FileManagementService
import com.dbExpose.services.StudentManagementService
import javax.ws.rs.*
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import org.json.JSONObject
import java.io.InputStream
import javax.inject.Inject
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import kotlin.jvm.Throws

@Path("/students")
class StudentsManagementResource @Inject constructor(
    private val studentManagementService: StudentManagementService,
    private val fileManagementService: FileManagementService
) {

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    @Deprecated("no longer in use")
    fun create(
        studentData: String
    ): Response {
        try {
            studentManagementService.createDoc(studentData)
            return Response.ok().build()
        } catch (e: Exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error processing file: ${e.message}").build()
        }
    }

    @POST
    @Path("/upload2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun create2(
        studentData: String
    ): String {
        val res=studentManagementService.createDoc2(studentData)
        return res.toString()
    }

    @POST
    @Path("/bulkUpload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun bulkCreate(
        studentsData: String
    ):String{
        val res=studentManagementService.bulkCreate(studentsData)
        return res.toString()
    }

    @POST
    @Path("/bulkUploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun uploadAndDisplayContent(
        @FormDataParam("file") fileInputStream: InputStream,
        @FormDataParam("file") contentDispositionHeader: FormDataContentDisposition,
//        @FormDataParam("metaData") metadata:FormDataBodyPart
    ) : String {
        try{
            val fileName=contentDispositionHeader.fileName
            val result=fileManagementService.saveFile(fileInputStream,fileName)
            val response=studentManagementService.bulkCreate(result)
            return response.toString()
        }catch(e:Exception){
            val errorMessage = "Error uploading file: ${e.message}"
            return errorMessage
        }
    }

    @GET
    @Path("/get/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(
        @PathParam("uuid") id: String
    ): String {
        try {
            if (id.isNotEmpty()) {
                return studentManagementService.getById(id).toString()
            }
        } catch (e: Exception) {
            return Response.status(Response.Status.BAD_REQUEST).toString()
        }
        return ""
    }

    @GET
    @Path("/getCount")
    fun getCount(): Response {
        val count = studentManagementService.getCount().toString()
        return Response.ok("The total number of students are: $count").build()
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    fun deleteStudent(
        @QueryParam("name") name:String
    ):String{
        val res=studentManagementService.deleteByName("name",name)
        return res
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateData(
        @QueryParam("uuid") id:String,
        studentData: String
    ):String{

        try{
            val res=studentManagementService.updateData(id,JSONObject(studentData))
            return "Successfully updated the document with uuid:$id \n\n $res"
        }catch (e:Exception){
            return "failure:${e.message}"
        }
    }

}