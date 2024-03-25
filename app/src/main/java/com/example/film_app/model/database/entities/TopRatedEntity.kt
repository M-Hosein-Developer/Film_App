package com.example.movies.model.dataClasses

import com.example.movies.utils.TOP_RATED
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TOP_RATED)
data class TopRatedEntity(
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
