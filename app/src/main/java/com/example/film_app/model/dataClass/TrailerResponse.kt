package com.example.film_app.model.dataClass

data class TrailerResponse(
    val id: Int,
    val results: List<MoviesResult>
) {
    data class MoviesResult(
        val id: String,
        val iso_3166_1: String,
        val iso_639_1: String,
        val key: String,
        val name: String,
        val official: Boolean,
        val published_at: String,
        val site: String,
        val size: Int,
        val type: String
    )
}