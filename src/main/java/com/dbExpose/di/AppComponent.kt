package com.dbExpose.di

//import com.dbExpose.repositories.ItemRepository
import com.dbExpose.DB_NAME
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.glassfish.grizzly.http.server.HttpServer
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules=[DatabaseModule::class,ConfigModule::class,HttpModule::class])
interface AppComponent {
   fun dbInfo():MongoClient
   fun db():MongoDatabase
   fun server(): HttpServer
}