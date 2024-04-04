package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.film_app.ui.intent.SearchIntent
import com.example.film_app.ui.state.searchState.SearchState
import com.example.film_app.util.EMPTY_DATA
import com.example.film_app.viewModel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {

    val searchText by remember { mutableStateOf("go") }
    var dataBySearch by remember { mutableStateOf(listOf(EMPTY_DATA)) }

    LaunchedEffect(searchText) {

        viewModel.dataIntent.send(SearchIntent.FetchDataBySearch(searchText))

        viewModel.getDataBySearch.collect{

            when(it){

                is SearchState.Idle -> {}
                is SearchState.Loading ->{}
                is SearchState.GetDataBySearch -> {dataBySearch = it.dataBySearch}
                is SearchState.Error -> {}

            }

        }

    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchToolbar { navController.popBackStack() }

        Log.v("testDataSearch" , dataBySearch.toString())


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchToolbar(onBackCLicked: () -> Unit) {

    TopAppBar(
        title = { Text(
            text = "Watch List",
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
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}
