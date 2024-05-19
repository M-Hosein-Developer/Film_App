package com.example.film_app.model.repository.filmCoverRepo

import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.AllDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmCoverRepositoryImpl @Inject constructor(private val dao: MyDao) : FilmCoverRepository {

    override val getAllDataCoverFilm: Flow<List<AllDataEntity>> = flow {
        emit(dao.getAllData())
    }.flowOn(Dispatchers.IO)

}