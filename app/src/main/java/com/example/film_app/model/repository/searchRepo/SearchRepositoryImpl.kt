package com.example.film_app.model.repository.searchRepo

import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val myDao: MyDao) : SearchRepository {

    override suspend fun getAllDataBySearch(title: String?): Flow<List<AllDataEntity>> = flow {

        emit(myDao.getAllDataBySearch(title))

    }.flowOn(Dispatchers.IO)


}