package com.example.film_app.ui.state

import com.example.film_app.model.database.entities.TopRatedEntity

sealed class TopRateState {

    data object Idle : TopRateState()
    data object Loading : TopRateState()
    data class TopRateData(val topRate : List<TopRatedEntity>) : TopRateState()
    data class TopRateError(val error: String?) : TopRateState()

}