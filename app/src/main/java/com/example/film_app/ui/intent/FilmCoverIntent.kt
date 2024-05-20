package com.example.film_app.ui.intent

sealed class FilmCoverIntent {

    data object FetchFilmCover : FilmCoverIntent()

}