package com.example.film_app.ui.intent

sealed class SearchIntent {

    data class FetchDataBySearch(val search : String?) : SearchIntent()

}