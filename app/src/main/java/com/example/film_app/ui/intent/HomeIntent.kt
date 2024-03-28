package com.example.film_app.ui.intent

sealed class HomeIntent {

    data object fetchNowPlaying : HomeIntent()
    data object fetchPopular : HomeIntent()
    data object fetchTopRate : HomeIntent()
    data object fetchUpcoming : HomeIntent()

}