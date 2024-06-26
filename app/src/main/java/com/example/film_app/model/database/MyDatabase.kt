package com.example.film_app.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.model.database.entities.DynamicTheme
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.TrendEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import com.example.film_app.model.database.entities.WatchListEntity

@Database(entities = [NowPlayingEntity::class , PopularEntity::class , TopRatedEntity::class , UpcomingEntity::class , TrendEntity::class , AllDataEntity::class , WatchListEntity::class , DynamicTheme::class] , version = 1 , exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao() : MyDao

}