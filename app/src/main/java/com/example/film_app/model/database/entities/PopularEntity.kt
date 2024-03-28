package com.example.film_app.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.film_app.util.POPULAR_TABLE

@Entity(tableName = POPULAR_TABLE)
data class PopularEntity(
    val adult: Boolean,
    val backdropPath: String,
  @PrimaryKey(autoGenerate = false)
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
