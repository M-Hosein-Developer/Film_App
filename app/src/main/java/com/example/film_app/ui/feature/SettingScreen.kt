package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.film_app.model.database.entities.DynamicTheme
import com.example.film_app.ui.intent.SettingIntent
import com.example.film_app.ui.state.settingState.GetDynamicThemeState
import com.example.film_app.ui.state.settingState.SetDynamicThemeState
import com.example.film_app.viewModel.SettingViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingViewModel) {

    var dynamicThemeState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.dataIntent.send(SettingIntent.GetDynamicThemeIntent)
    }

    LaunchedEffect(Unit) {
        viewModel.setDynamicThemeState.collect{
            when(it){
                is SetDynamicThemeState.Idle -> {}
                is SetDynamicThemeState.ThemeStateSet -> {
                    Log.v("testDataTheme" , it.state)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDynamicThemeState.collect{
            when(it){
                is GetDynamicThemeState.Idle -> {}
                is GetDynamicThemeState.ThemeStateSet -> {
                    dynamicThemeState = it.state
                    Log.v("testDataTheme" , it.state.toString())
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {

        SettingToolbar {
            navController.popBackStack()
        }

        DynamicThemeSetting(dynamicThemeState){
            dynamicThemeState = it
            scope.launch {
                viewModel.dataIntent.send(SettingIntent.SendDynamicThemeIntent(DynamicTheme(1, dynamicThemeState)))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Translator {}

        Spacer(modifier = Modifier.height(12.dp))



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingToolbar(onBackPressed :() -> Unit){

    TopAppBar(
        title = { Text( text = "Setting",
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed.invoke() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun DynamicThemeSetting(dynamicThemeState : Boolean , onDynamicClicked : (Boolean) -> Unit){

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Dynamic Theme",
            style = TextStyle(
                fontSize = 18.sp
            )
        )

        Switch(
            checked = dynamicThemeState,
            onCheckedChange = {
                onDynamicClicked.invoke(it)
            },
            thumbContent = {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

    }

}

@Composable
fun Translator(onTranslateClicked : (Boolean) -> Unit){

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Translate",
            style = TextStyle(
                fontSize = 18.sp
            )
        )

        Switch(
            checked = false,
            onCheckedChange = {
                onTranslateClicked.invoke(it)
            },
            thumbContent = {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

    }

}