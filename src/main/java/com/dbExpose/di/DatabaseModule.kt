package com.dbExpose.di

import com.dbExpose.DB_HOST
import com.dbExpose.DB_NAME
import com.dbExpose.DB_PORT
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideMongoClient(
        @Named(DB_HOST) host:String,
        @Named(DB_PORT) port:Int
    ): MongoClient {
        //return client
        println("$host,$port")
        return MongoClients.create("mongodb://${host}:${port}")
    }

    @Provides
    fun provideDatabase(
        mongoClient: MongoClient,
        @Named(DB_NAME) databaseName: String
    ):MongoDatabase{
        return mongoClient.getDatabase(databaseName)
    }

}