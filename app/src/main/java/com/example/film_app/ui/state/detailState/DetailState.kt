package com.example.film_app.ui.state.detailState

import com.example.film_app.model.database.entities.AllDataEntity

sealed class DetailState {

    data object Idle : DetailState()
    data object Loading : DetailState()
    data class AllData(val allData : List<AllDataEntity>) : DetailState()
    data class AllDataError(val allDataError : String?) : DetailState()

}