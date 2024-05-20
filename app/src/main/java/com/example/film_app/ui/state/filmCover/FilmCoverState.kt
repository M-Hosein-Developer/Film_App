package com.example.film_app.ui.state.filmCover

import com.example.film_app.model.database.entities.AllDataEntity

sealed class FilmCoverState {

    data object Idle : FilmCoverState()
    data class FetchData(val data : List<AllDataEntity>)
    data class Error(val error : String?)

}