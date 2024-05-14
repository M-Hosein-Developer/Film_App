package com.example.film_app.ui.intent

import com.example.film_app.model.database.entities.DynamicTheme

sealed class SettingIntent {

    data class DynamicThemeIntent(val state : DynamicTheme) : SettingIntent()

}