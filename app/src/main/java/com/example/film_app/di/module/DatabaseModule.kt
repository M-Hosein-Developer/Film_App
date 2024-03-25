package com.example.film_app.di.module

import android.content.Context
import androidx.room.Room
import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : MyDatabase = Room
        .databaseBuilder(context , MyDatabase::class.java , "MyDB.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideMyDao(myDatabase: MyDatabase) : MyDao = myDatabase.myDao()

}