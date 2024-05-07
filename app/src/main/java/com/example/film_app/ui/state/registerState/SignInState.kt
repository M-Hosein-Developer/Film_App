package com.example.film_app.ui.state.registerState

sealed class SignInState {

    data object Idle : SignInState()
    data class IsRegister(val success : String) : SignInState()
    data class RegisterError(val error : String?) : SignInState()

}