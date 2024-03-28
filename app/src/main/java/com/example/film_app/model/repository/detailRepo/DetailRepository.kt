package com.example.film_app.model.repository.detailRepo

import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    val allDate : Flow<List<AllDataEntity>>

}