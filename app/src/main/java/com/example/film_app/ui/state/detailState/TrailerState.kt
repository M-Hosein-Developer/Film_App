package com.example.film_app.ui.state.detailState

import com.example.film_app.model.dataClass.TrailerResponse

sealed class TrailerState {

    data object Idle : TrailerState()
    data object Loading : TrailerState()
    data class TrailerDataById(val trailerData : List<TrailerResponse.MoviesResult>) : TrailerState()
    data class TrailerError(val error : String?) : TrailerState()

}