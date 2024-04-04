package com.example.film_app.ui.state.searchState

import com.example.film_app.model.database.entities.AllDataEntity

sealed class SearchState {

    data object Idle : SearchState()
    data object Loading : SearchState()
    data class GetDataBySearch(val dataBySearch : List<AllDataEntity>) : SearchState()
    data class Error(val error : String?) : SearchState()

}