package com.example.film_app.util

import com.example.film_app.model.dataClass.ResponseMovies
import com.example.film_app.model.database.entities.NowPlayingEntity

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