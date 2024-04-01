package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.WatchListState
import com.example.film_app.viewModel.DetailAndWatchListViewModel

@Composable
fun WatchListScreen(viewModel: DetailAndWatchListViewModel) {

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


    Log.v("watchListData" , watchListData.toString())

}