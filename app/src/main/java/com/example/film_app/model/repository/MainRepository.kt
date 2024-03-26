package com.example.film_app.model.repository

import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    //Now Playing
    val nowPlaying : Flow<List<NowPlayingEntity>>

    //Popular
    val popular : Flow<List<PopularEntity>>

    //Top Rate
    val topRate : Flow<List<TopRatedEntity>>

    //Upcoming
    val upcoming : Flow<List<UpcomingEntity>>




}