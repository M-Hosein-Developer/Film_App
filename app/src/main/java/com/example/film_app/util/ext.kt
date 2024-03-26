package com.example.film_app.util

import com.example.film_app.model.dataClass.ResponseMovies
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.UpcomingEntity

fun getAllNowPlay(response : ResponseMovies): List<NowPlayingEntity> {

    val data = arrayListOf<NowPlayingEntity>()

    for (i in 0 until response.results.size) {
        val result = response.results[i]
        val movieEntity = NowPlayingEntity(
            result.adult,
            result.backdropPath,
            result.id,
            result.originalLanguage,
            result.originalTitle,
            result.overview,
            result.popularity,
            result.posterPath,
            result.releaseDate,
            result.title,
            result.video,
            result.voteAverage,
            result.voteCount
        )
        data.add(movieEntity)
    }

    return data
}

fun getAllPopular(response : ResponseMovies): List<PopularEntity> {

    val data = arrayListOf<PopularEntity>()

    for (i in 0 until response.results.size) {
        val result = response.results[i]
        val movieEntity = PopularEntity(
            result.adult,
            result.backdropPath,
            result.id,
            result.originalLanguage,
            result.originalTitle,
            result.overview,
            result.popularity,
            result.posterPath,
            result.releaseDate,
            result.title,
            result.video,
            result.voteAverage,
            result.voteCount
        )
        data.add(movieEntity)
    }

    return data
}

fun getAllTopRate(response : ResponseMovies): List<TopRatedEntity> {

    val data = arrayListOf<TopRatedEntity>()

    for (i in 0 until response.results.size) {
        val result = response.results[i]
        val movieEntity = TopRatedEntity(
            result.adult,
            result.backdropPath,
            result.id,
            result.originalLanguage,
            result.originalTitle,
            result.overview,
            result.popularity,
            result.posterPath,
            result.releaseDate,
            result.title,
            result.video,
            result.voteAverage,
            result.voteCount
        )
        data.add(movieEntity)
    }

    return data
}

fun getAllUpcoming(response : ResponseMovies): List<UpcomingEntity> {

    val data = arrayListOf<UpcomingEntity>()

    for (i in 0 until response.results.size) {
        val result = response.results[i]
        val movieEntity = UpcomingEntity(
            result.adult,
            result.backdropPath,
            result.id,
            result.originalLanguage,
            result.originalTitle,
            result.overview,
            result.popularity,
            result.posterPath,
            result.releaseDate,
            result.title,
            result.video,
            result.voteAverage,
            result.voteCount
        )
        data.add(movieEntity)
    }

    return data
}