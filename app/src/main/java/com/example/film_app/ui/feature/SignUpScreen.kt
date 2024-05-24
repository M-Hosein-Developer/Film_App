package com.example.film_app.ui.feature

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.film_app.ui.intent.RegisterIntent
import com.example.film_app.ui.state.registerState.SignUpState
import com.example.film_app.util.BottomNavItem
import com.example.film_app.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavHostController, viewModel: RegisterViewModel) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signUpState.collect {
            when (it) {
                is SignUpState.Idle -> {}
                is SignUpState.IsRegister -> {
                    Toast.makeText(context, it.success, Toast.LENGTH_SHORT).show()
                    navController.navigate(BottomNavItem.HomeScreen.rout) {
                        popUpTo(BottomNavItem.SignUpScreen.rout) {
                            inclusive = true
                        }
                    }
                }

                is SignUpState.RegisterError -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        UsernameSignUp(values = username, label = "Email") {
            username = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        PasswordSignUp(values = password, label = "Password") {
            password = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        PasswordSignUp(values = confirmPassword, label = "Confirm Password") {
            confirmPassword = it
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (password == confirmPassword && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                    scope.launch {
                        viewModel.dataIntent.send(RegisterIntent.SignUp(username, password))
                    }

                } else {
                    Toast.makeText(
                        context,
                        "Complete the field or check password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SignInText {
            navController.navigate(BottomNavItem.SignInScreen.rout) {
                popUpTo(BottomNavItem.SignUpScreen.rout){
                    inclusive = true
                }
            }
        }

    }

}

@Composable
fun UsernameSignUp(values : String , label : String , onValueChange : (String) -> Unit){
    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
        },
        value = values,
        label = { Text(label) },
        onValueChange = {onValueChange.invoke(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun PasswordSignUp(values : String , label : String , onValueChange : (String) -> Unit){
    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null
            )
        },
        value = values,
        label = { Text(label) },
        onValueChange = {onValueChange.invoke(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignInText(onClickSignUp : () -> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = "have an account?")

        TextButton(onClick = { onClickSignUp.invoke() }) {
            Text(text = "Sign In")
        }

    }

}