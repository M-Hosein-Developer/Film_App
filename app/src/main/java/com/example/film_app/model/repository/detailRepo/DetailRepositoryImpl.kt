package com.example.film_app.model.repository.detailRepo

import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val myDao: MyDao) : DetailRepository {

    override val allDate: Flow<List<AllDataEntity>> = flow {



    }


}