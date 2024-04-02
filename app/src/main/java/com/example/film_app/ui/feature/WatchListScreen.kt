package com.example.film_app.ui.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.WatchListState
import com.example.film_app.viewModel.DetailAndWatchListViewModel

@Composable
fun WatchListScreen(viewModel: DetailAndWatchListViewModel, navController: NavHostController) {

    var watchListData by remember { mutableStateOf(listOf(WatchListEntity(-1))) }


    LaunchedEffect(1) {
        viewModel.dataIntent.send(DetailAndWatchListIntent.FetchWatchListId(WatchListEntity(-1)))

        viewModel.watchListState.collect{
            when(it){

                is WatchListState.Idle -> {}
                is WatchListState.Loading -> {}
                is WatchListState.WatchList -> { watchListData = it.watchList }
                is WatchListState.WatchListError -> {}

            }


        }

    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WatchListToolbar {
            navController.popBackStack()
        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListToolbar(
    onBackCLicked: () -> Unit,
) {

    TopAppBar(
        title = { Text(
            text = "Watch List",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}