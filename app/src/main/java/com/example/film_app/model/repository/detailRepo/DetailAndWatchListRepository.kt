package com.example.film_app.model.repository.detailRepo

import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.movies.model.apiService.TrailerResponse
import kotlinx.coroutines.flow.Flow

interface DetailAndWatchListRepository {

    val allDate : Flow<List<AllDataEntity>>

    val watchList : Flow<List<WatchListEntity>>

    suspend fun insertWatchList(watchList : WatchListEntity)

    suspend fun deleteWatchListById(id : Int)

    suspend fun trailerById(id: Int) : Flow<List<TrailerResponse.MoviesResult>>

}