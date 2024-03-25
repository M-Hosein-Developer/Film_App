package com.example.movies.model.dataClasses

import com.example.movies.utils.UPCOMING
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UPCOMING)
data class UpcomingEntity(
    val adult: Boolean,
    val backdropPath: String,
  @PrimaryKey(autoGenerate = true)
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
