package com.example.film_app.model.repository.searchRepo

import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getAllDataBySearch(title : String?) : Flow<List<AllDataEntity>>

}