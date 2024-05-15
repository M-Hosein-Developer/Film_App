package com.example.film_app.ui.intent

import com.example.film_app.model.database.entities.DynamicTheme

sealed class SettingIntent {

    data class SendDynamicThemeIntent(val state : DynamicTheme) : SettingIntent()
    data object GetDynamicThemeIntent : SettingIntent()

}