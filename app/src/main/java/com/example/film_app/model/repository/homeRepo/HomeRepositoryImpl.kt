package com.example.film_app.model.repository.homeRepo

import com.example.film_app.model.apiService.ApiService
import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.TrendEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import com.example.film_app.util.getAllData
import com.example.film_app.util.getAllNowPlay
import com.example.film_app.util.getAllPopular
import com.example.film_app.util.getAllTopRate
import com.example.film_app.util.getAllTrend
import com.example.film_app.util.getAllUpcoming
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val myDao: MyDao,
) : HomeRepository {

    //Now Playing
    override val nowPlaying: Flow<List<NowPlayingEntity>> = flow {
        while (true){
            val response = apiService.getAllNowPlay()
            emit(getAllNowPlay(response))
            myDao.insertAllNowPlayingData(getAllNowPlay(response))
            myDao.insertAllData(getAllData(response))
            delay(20000)
        }
    }.flowOn(Dispatchers.IO)


    override val nowPlayingByDb: Flow<List<NowPlayingEntity>> = flow {
        emit(myDao.getAllNowPlayingData())
    }.flowOn(Dispatchers.IO)


    //Popular
    override val popular: Flow<List<PopularEntity>> = flow {
        while (true){
            val response = apiService.getAllPopular()
            emit(getAllPopular(response))
            myDao.insertAllPopularData(getAllPopular(response))
            myDao.insertAllData(getAllData(response))
            delay(21000)
        }
    }.flowOn(Dispatchers.IO)

    override val popularByDb: Flow<List<PopularEntity>> = flow {
        emit(myDao.getAllPopularData())
    }.flowOn(Dispatchers.IO)

    //Top Rate
    override val topRate: Flow<List<TopRatedEntity>> = flow {
        while (true){
            val response = apiService.getAllTopRate()
            emit(getAllTopRate(response))
            myDao.insertAllTopRatedData(getAllTopRate(response))
            myDao.insertAllData(getAllData(response))
            delay(22000)
        }
    }.flowOn(Dispatchers.IO)

    override val topRateByDb: Flow<List<TopRatedEntity>> = flow {
        emit(myDao.getAllTopRatedData())
    }.flowOn(Dispatchers.IO)

    //Upcoming
    override val upcoming: Flow<List<UpcomingEntity>> = flow {
        while (true){
            val response = apiService.getAllUpcoming()
            emit(getAllUpcoming(response))
            myDao.insertUpcomingData(getAllUpcoming(response))
            myDao.insertAllData(getAllData(response))
            delay(23000)
        }
    }.flowOn(Dispatchers.IO)

    override val upcomingByDb: Flow<List<UpcomingEntity>> = flow {
        emit(myDao.getAllUpcomingData())
    }.flowOn(Dispatchers.IO)

    //Trend
    override val trend: Flow<List<TrendEntity>> = flow {
        while (true){
            val response = apiService.getAllTrend()
            emit(getAllTrend(response))
            myDao.insertTrendData(getAllTrend(response))
            myDao.insertAllData(getAllData(response))
            delay(24000)
        }
    }.flowOn(Dispatchers.IO)

    override val trendByDb: Flow<List<TrendEntity>> = flow {
        emit(myDao.getAllTrendData())
    }.flowOn(Dispatchers.IO)


}