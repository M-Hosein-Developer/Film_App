package com.example.film_app.ui.state

import com.example.film_app.model.database.entities.PopularEntity

sealed class PopularState {

    data object Idle : PopularState()
    data object Loading : PopularState()
    data class PopularData(val nowPlying : List<PopularEntity>) : PopularState()
    data class PopularError(val error: String?) : PopularState()

}