package com.example.film_app.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.film_app.util.WATCHLIST

@Entity(tableName = WATCHLIST)
data class WatchListEntity(

    @PrimaryKey(autoGenerate = false)
    val moviesId : Int

)
