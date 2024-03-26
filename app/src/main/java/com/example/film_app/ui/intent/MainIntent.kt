package com.example.film_app.ui.intent

sealed class MainIntent {

    data object fetchNowPlaying : MainIntent()

}