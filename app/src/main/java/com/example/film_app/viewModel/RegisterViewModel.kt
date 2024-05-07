package com.example.film_app.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.ui.intent.RegisterIntent
import com.example.film_app.ui.state.registerState.SignInState
import com.example.film_app.ui.state.registerState.SignUpState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val sharedPrf : SharedPreferences) : ViewModel(){

    val dataIntent = Channel<RegisterIntent>()

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState : StateFlow<SignUpState> get() = _signUpState

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState : StateFlow<SignInState> get() = _signInState


    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){
                    is RegisterIntent.SignUp -> signUpFire(it.username , it.password)
                    is RegisterIntent.SignIn -> signInFire(it.username , it.password)
                }

            }

        }

    }

    private fun signInFire(username: String, password: String) {

        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(
            username,
           password
        )
            .addOnCompleteListener(){ task ->
                if (task.isSuccessful) {

                    val editor = sharedPrf.edit()
                    editor.putString("signIn", "successful")
                    editor.apply()

                    _signInState.value = SignInState.IsRegister("SignIn Successful")

                } else {
                    _signInState.value = SignInState.RegisterError("SignIn Not Successful")
                }

            }

    }

    private fun signUpFire(username: String, password: String) {

        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(
            username, password
        ).addOnCompleteListener() { task ->

            if (task.isSuccessful) {

                val editor = sharedPrf.edit()
                editor.putString("signIn", "successful")
                editor.apply()

                _signUpState.value = SignUpState.IsRegister("Register Successful")
            } else {
                Log.v("testFirebase" , task.toString())
               _signUpState.value = SignUpState.RegisterError("Register Not Successful")
            }


        }
    }



}