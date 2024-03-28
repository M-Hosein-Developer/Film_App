package com.example.film_app.ui.state.homeState

import com.example.film_app.model.database.entities.UpcomingEntity

sealed class UpcomingState {

    data object Idle : UpcomingState()
    data object Loading : UpcomingState()
    data class UpcomingData(val upcoming : List<UpcomingEntity>) : UpcomingState()
    data class UpcomingError(val error: String?) : UpcomingState()

}