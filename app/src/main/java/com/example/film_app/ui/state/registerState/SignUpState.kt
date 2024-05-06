package com.example.film_app.ui.state.registerState

sealed class SignUpState {

    data object Idle : SignUpState()
    data class IsRegister(val success : String) : SignUpState()
    data class RegisterError(val error : String?) : SignUpState()

}