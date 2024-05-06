package com.example.film_app.ui.intent

sealed class RegisterIntent() {

    data class SignUp(val username : String , val password : String) : RegisterIntent()

}