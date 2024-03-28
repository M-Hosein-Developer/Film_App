package com.example.film_app.ui.intent

sealed class DetailIntent {

    data object fetchTrend : DetailIntent()

}