package com.example.film_app.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.model.dataClasses.NowPlayingEntity
import com.example.movies.model.dataClasses.PopularEntity
import com.example.movies.model.dataClasses.TopRatedEntity
import com.example.movies.model.dataClasses.TrendEntity
import com.example.movies.model.dataClasses.UpcomingEntity

@Database(entities = [NowPlayingEntity::class , PopularEntity::class , TopRatedEntity::class , UpcomingEntity::class , TrendEntity::class] , version = 1 , exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao() : MyDao

}