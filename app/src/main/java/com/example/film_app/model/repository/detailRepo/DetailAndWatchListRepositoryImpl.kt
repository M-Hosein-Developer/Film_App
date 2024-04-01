package com.example.film_app.model.repository.detailRepo

import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.model.database.entities.WatchListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailAndWatchListRepositoryImpl @Inject constructor(private val myDao: MyDao) : DetailAndWatchListRepository {

    override val allDate: Flow<List<AllDataEntity>> = flow {
        while (true){
            emit(myDao.getAllData())
            delay(25000)
        }
    }.flowOn(Dispatchers.IO)

    override val watchList: Flow<List<WatchListEntity>> = flow {
        while (true){
            emit(myDao.getAllWatchList())
            delay(10000)
        }
    }


    override suspend fun insertWatchList(id: WatchListEntity) {
        myDao.insertWatchList(id)
    }

    override suspend fun deleteWatchListById(id: WatchListEntity) {
        myDao.deleteWatchListBtId(id.moviesId)
    }


}