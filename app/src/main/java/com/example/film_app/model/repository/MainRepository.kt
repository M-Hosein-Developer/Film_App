package com.example.film_app.model.repository

import com.example.film_app.model.database.entities.NowPlayingEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    val nowPlaying : Flow<List<NowPlayingEntity>>

}