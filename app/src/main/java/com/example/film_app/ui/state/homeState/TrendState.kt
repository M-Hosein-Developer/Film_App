package com.example.film_app.ui.state.homeState

import com.example.film_app.model.database.entities.TrendEntity

sealed class TrendState {

    data object Idle : TrendState()
    data object Loading : TrendState()
    data class TrendData(val trend : List<TrendEntity>) : TrendState()
    data class TrendError(val error: String?) : TrendState()

}