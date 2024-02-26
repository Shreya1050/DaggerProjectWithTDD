package com.dbExpose.di

import com.dbExpose.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConfigModule{

    @Provides
    @Named(DB_PORT)
    fun provideDbPort():Int{
        return 27017
    }

    @Singleton
    @Provides
    @Named(DB_NAME)
    fun provideDbName():String{
        return "students"
    }

    @Provides
    @Named(DB_COLLECTION)
    fun provideCollectionName():String{
        return "students.studentData"
    }

    @Provides
    @Named(DB_HOST)
    fun provideDbHost():String{
        return "localhost"
    }

    @Provides
    @Named(APP_IP)
    fun provideAppIP():String{
        return "http://0.0.0.0"
    }

    @Provides
    @Named(APP_PORT)
    fun provideAppPort():Int{
        return 8080
    }
}