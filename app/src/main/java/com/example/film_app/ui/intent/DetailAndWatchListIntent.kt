package com.example.film_app.ui.intent

import com.example.film_app.model.database.entities.WatchListEntity

sealed class DetailAndWatchListIntent {

    data object FetchAllData : DetailAndWatchListIntent()
    data class FetchWatchListId(val id : WatchListEntity) : DetailAndWatchListIntent()
    data class DeleteWatchListId(val id : WatchListEntity) : DetailAndWatchListIntent()

}