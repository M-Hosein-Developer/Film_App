package com.example.film_app.model.repository

import com.example.film_app.model.apiService.ApiService
import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.util.getAllNowPlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService , private val myDao: MyDao) : MainRepository {

    override val nowPlaying: Flow<List<NowPlayingEntity>> = flow {
        while (true){
            val response = apiService.getAllNowPlay()
            emit(getAllNowPlay(response))
            myDao.insertAllNowPlayingData(getAllNowPlay(response))
            delay(20000)
        }
    }.flowOn(Dispatchers.IO)


    
}