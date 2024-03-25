package com.example.film_app.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.film_app.util.NOWPLAYING_TABLE
import com.example.film_app.util.POPULAR_TABLE
import com.example.film_app.util.TOP_RATED
import com.example.film_app.util.TREND
import com.example.film_app.util.UPCOMING
import com.example.movies.model.dataClasses.NowPlayingEntity
import com.example.movies.model.dataClasses.PopularEntity
import com.example.movies.model.dataClasses.TopRatedEntity
import com.example.movies.model.dataClasses.TrendEntity
import com.example.movies.model.dataClasses.UpcomingEntity

@Dao
interface MyDao {

    //Now Playing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNowPlayingData(moviesList: List<NowPlayingEntity>)

    @Query("SELECT * FROM  $NOWPLAYING_TABLE")
    suspend fun getAllNowPlayingData() : List<NowPlayingEntity>


    //Popular
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularData(moviesList: List<PopularEntity>)

    @Query("SELECT * FROM  $POPULAR_TABLE")
    suspend fun getAllPopularData() : List<PopularEntity>


    //Top Rated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedData(moviesList: List<TopRatedEntity>)

    @Query("SELECT * FROM  $TOP_RATED")
    suspend fun getAllTopRatedData() : List<TopRatedEntity>


    //Upcoming
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingData(moviesList: List<UpcomingEntity>)

    @Query("SELECT * FROM  $UPCOMING")
    suspend fun getAllUpcomingData() : List<UpcomingEntity>


    //Trend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendData(moviesList: List<TrendEntity>)

    @Query("SELECT * FROM  $TREND")
    suspend fun getAllTrendData() : List<TrendEntity>

}