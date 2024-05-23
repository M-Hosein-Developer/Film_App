package com.example.film_app.ui.intent

sealed class HomeIntent {

    data object FetchNowPlaying : HomeIntent()
    data object FetchPopular : HomeIntent()
    data object FetchTopRate : HomeIntent()
    data object FetchUpcoming : HomeIntent()
    data object FetchTrend : HomeIntent()

}