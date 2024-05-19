package com.example.film_app.model.repository.filmCoverRepo

import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.flow.Flow

interface FilmCoverRepository {

    val getAllDataCoverFilm : Flow<List<AllDataEntity>>


}