package com.example.film_app.model.repository

import com.example.film_app.model.apiService.ApiService
import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.util.getAllNowPlay
import com.example.film_app.util.getAllPopular
import com.example.film_app.util.getAllTopRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService , private val myDao: MyDao) : MainRepository {

    //Now Playing
    override val nowPlaying: Flow<List<NowPlayingEntity>> = flow {
        while (true){
            val response = apiService.getAllNowPlay()
            emit(getAllNowPlay(response))
            myDao.insertAllNowPlayingData(getAllNowPlay(response))
            delay(20000)
        }
    }.flowOn(Dispatchers.IO)

    //Popular
    override val popular: Flow<List<PopularEntity>> = flow {
        while (true){
            val response = apiService.getAllPopular()
            emit(getAllPopular(response))
            myDao.insertAllPopularData(getAllPopular(response))
            delay(20000)
        }
    }.flowOn(Dispatchers.IO)

    //Top Rate
    override val topRate: Flow<List<TopRatedEntity>> = flow {
        while (true){
            val response = apiService.getAllTopRate()
            emit(getAllTopRate(response))
            myDao.insertAllTopRatedData(getAllTopRate(response))
            delay(20000)
        }
    }.flowOn(Dispatchers.IO)


}