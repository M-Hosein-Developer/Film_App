package com.example.film_app.ui.state.detailState

sealed class DeleteFromWatchListState {

    data object Idle : DeleteFromWatchListState()
    data object Loading : DeleteFromWatchListState()
    data class DeleteWatchListError(val deleteWatchListError : String?) : DeleteFromWatchListState()

}