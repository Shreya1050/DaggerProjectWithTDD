package com.dbExpose

import com.dbExpose.di.ConfigModule
import com.dbExpose.di.DaggerAppComponent
import org.glassfish.grizzly.http.server.HttpServer
import java.io.IOException


fun main(args : Array<String>) {

    val configModule = ConfigModule()
    println(configModule)

    val applicationComp = DaggerAppComponent.builder().configModule(configModule).build()
    val httpServer = applicationComp.server()


    try {
        httpServer.start()
        println("HTTP server started. Press any key to stop.")
        System.`in`.read()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        httpServer.shutdownNow()
    }

    Thread.currentThread().join()
}