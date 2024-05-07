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
import com.example.film_app.ui.state.registerState.SignInState
import com.example.film_app.util.BottomNavItem
import com.example.film_app.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navController: NavHostController, registerViewModel: RegisterViewModel) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        registerViewModel.signInState.collect {
            when (it) {
                is SignInState.Idle -> {}
                is SignInState.IsRegister -> {
                    Toast.makeText(context, it.success, Toast.LENGTH_SHORT).show()
                }
                is SignInState.RegisterError -> {
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

        Username(values = username, label = "Email") {
            username = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        Password(values = password, label = "Password") {
            password = it
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (password.isNotEmpty() && username.isNotEmpty()) {
                    scope.launch {
                        registerViewModel.dataIntent.send(RegisterIntent.SignIn(username, password))
                    }
                    navController.navigate(BottomNavItem.HomeScreen.rout) {
                        popUpTo(BottomNavItem.SignInScreen.rout) {
                            inclusive = true
                        }
                    }

                    Toast.makeText(context, "SignIn Successful", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(
                        context,
                        "Sign In Failed",
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
                text = "Sign In",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SignUpText {
            navController.navigate(BottomNavItem.SignUpScreen.rout) {
                popUpTo(BottomNavItem.SignInScreen.rout) {
                    inclusive = true
                }
            }
        }

    }

}

@Composable
fun Username(values : String , label : String , onValueChange : (String) -> Unit){
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
fun Password(values : String , label : String , onValueChange : (String) -> Unit){
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
fun SignUpText(onClickSignUp : () -> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = "Don't have an account?")

        TextButton(onClick = { onClickSignUp.invoke() }) {
            Text(text = "Sign Up")
        }

    }

}
