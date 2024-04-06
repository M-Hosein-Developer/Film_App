package com.example.film_app.model.apiService

import com.example.film_app.model.dataClass.ResponseMovies
import com.example.film_app.util.API_KEY
import com.example.movies.model.apiService.TrailerResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers(API_KEY)
    @GET("3/movie/now_playing?language=en-US&page=1")
    suspend fun getAllNowPlay() : ResponseMovies

    @Headers(API_KEY)
    @GET("3/movie/popular?language=en-US&page=1")
    suspend fun getAllPopular() : ResponseMovies

    @Headers(API_KEY)
    @GET("3/movie/top_rated?language=en-US&page=1")
    suspend fun getAllTopRate() : ResponseMovies

    @Headers(API_KEY)
    @GET("3/movie/upcoming?language=en-US&page=1")
    suspend fun getAllUpcoming() : ResponseMovies

    @Headers(API_KEY)
    @GET("3/trending/all/day?language=en-US")
    suspend fun getAllTrend() : ResponseMovies

    @Headers(API_KEY)
    @GET("3/movie/{id}/videos?language=en-US")
    suspend fun getTrailerById(@Path("id") id : Int) : TrailerResponse


}