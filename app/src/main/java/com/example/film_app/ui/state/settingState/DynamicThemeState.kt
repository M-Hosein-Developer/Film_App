package com.example.film_app.ui.state.settingState

sealed class DynamicThemeState {

    data object Idle : DynamicThemeState()
    data class ThemeState(val state : Boolean) : DynamicThemeState()
    data class ThemeStateError(val error : String?) :DynamicThemeState()

}