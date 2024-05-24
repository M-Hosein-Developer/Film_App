package com.example.film_app.model.repository.homeRepo

import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.TrendEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    //Now Playing
    val nowPlaying : Flow<List<NowPlayingEntity>>
    val nowPlayingByDb : Flow<List<NowPlayingEntity>>

    //Popular
    val popular : Flow<List<PopularEntity>>
    val popularByDb : Flow<List<PopularEntity>>

    //Top Rate
    val topRate : Flow<List<TopRatedEntity>>
    val topRateByDb : Flow<List<TopRatedEntity>>

    //Upcoming
    val upcoming : Flow<List<UpcomingEntity>>
    val upcomingByDb : Flow<List<UpcomingEntity>>

    //Trend
    val trend : Flow<List<TrendEntity>>
    val trendByDb : Flow<List<TrendEntity>>


}