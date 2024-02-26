package com.dbExpose.di

import com.dbExpose.APP_IP
import com.dbExpose.APP_PORT
//import com.dbExpose.resources.DocumentManagementResource
import com.dbExpose.resources.FileManagementResource
import com.dbExpose.resources.StudentsManagementResource
//import com.dbExpose.services.Create
import dagger.Module
import dagger.Provides
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.server.ResourceConfig
import javax.inject.Named
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.media.multipart.MultiPartFeature
import javax.inject.Singleton
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.UriBuilder


@Module
class HttpModule {

    @Provides
    fun provideRestApplicationBootstrap(
        studentsManagementResource: StudentsManagementResource,
        fileManagementResource: FileManagementResource,
//        documentManagementResource: DocumentManagementResource,
//        studentInfo: StudentInfo
    ): ResourceConfig {
        return ResourceConfig()
            .register(MultiPartFeature::class.java)
            .register(studentsManagementResource)
            .register(fileManagementResource)
//            .register(documentManagementResource)
//            .register(studentInfo::class.java)

    }

    @Provides
    fun provideHttpServer(
        @Named(APP_IP) host: String,
        @Named(APP_PORT) port: Int,
        application: ResourceConfig
    ): HttpServer {
       // Log.info("Rest Service will start on $host : $port")
        val uri = UriBuilder.fromUri(host).port(port).build()
        val server = GrizzlyHttpServerFactory.createHttpServer(uri, application, false)
        return server
    }

    @Singleton
    @Provides
    fun provideHttpClient(): Client {
        return ClientBuilder.newBuilder().build()
    }

//    fun stopServer(server: HttpServer) {
//        server.shutdown()
//    }
}