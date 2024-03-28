package com.example.film_app.ui.state.homeState

import com.example.film_app.model.database.entities.PopularEntity

sealed class PopularState {

    data object Idle : PopularState()
    data object Loading : PopularState()
    data class PopularData(val popular : List<PopularEntity>) : PopularState()
    data class PopularError(val error: String?) : PopularState()

}