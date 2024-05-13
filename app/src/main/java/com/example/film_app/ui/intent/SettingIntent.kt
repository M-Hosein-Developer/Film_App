package com.example.film_app.ui.intent

sealed class SettingIntent {

    data class DynamicThemeIntent(val state : Boolean) : SettingIntent()

}