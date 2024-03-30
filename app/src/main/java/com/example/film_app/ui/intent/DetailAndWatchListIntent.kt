package com.example.film_app.ui.intent

import com.example.film_app.model.database.entities.WatchListEntity

sealed class DetailAndWatchListIntent {

    data object fetchAllData : DetailAndWatchListIntent()

}