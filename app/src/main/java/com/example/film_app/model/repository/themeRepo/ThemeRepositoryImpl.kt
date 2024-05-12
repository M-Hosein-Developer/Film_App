package com.example.film_app.model.repository.themeRepo

import com.example.film_app.model.database.MyDao
import com.example.film_app.model.database.entities.DynamicTheme
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(private val myDao: MyDao) : ThemeRepository {


    override suspend fun insertDynamicThemeStateRep(state: DynamicTheme) = myDao.insertDynamicTheme(state)

    override suspend fun getDynamicThemeState(): DynamicTheme = myDao.getDynamicThemeState()


}