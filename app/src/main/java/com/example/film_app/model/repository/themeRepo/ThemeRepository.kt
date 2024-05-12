package com.example.film_app.model.repository.themeRepo

import com.example.film_app.model.database.entities.DynamicTheme

interface ThemeRepository {

    suspend fun insertDynamicThemeStateRep(state : DynamicTheme)

    suspend fun getDynamicThemeState() : DynamicTheme

}