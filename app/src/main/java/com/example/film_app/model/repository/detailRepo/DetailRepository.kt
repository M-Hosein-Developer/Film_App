package com.example.film_app.model.repository.detailRepo

import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.model.database.entities.WatchListEntity
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    val allDate : Flow<List<AllDataEntity>>

    suspend fun insertWatchList(id : WatchListEntity)

    val watchList : Flow<List<WatchListEntity>>


}