package com.example.film_app.ui.state.settingState

sealed class SetDynamicThemeState {

    data object Idle : SetDynamicThemeState()
    data class ThemeStateSet(val state : String) : SetDynamicThemeState()

}