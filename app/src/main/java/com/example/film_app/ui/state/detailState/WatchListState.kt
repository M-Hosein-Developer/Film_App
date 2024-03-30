package com.example.film_app.ui.state.detailState

import com.example.film_app.model.database.entities.WatchListEntity

sealed class WatchListState {

    data object Idle : WatchListState()
    data object Loading : WatchListState()
    data class WatchList(val watchList : List<WatchListEntity>) : WatchListState()
    data class WatchListError(val watchListError : String?) : WatchListState()

}