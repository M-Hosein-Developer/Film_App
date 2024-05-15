package com.example.film_app.ui.state.settingState

sealed class GetDynamicThemeState {

    data object Idle : GetDynamicThemeState()
    data class ThemeStateSet(val state : Boolean) : GetDynamicThemeState()

}