package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.film_app.ui.intent.MainIntent
import com.example.film_app.ui.state.NowPlayingState
import com.example.film_app.ui.state.PopularState
import com.example.film_app.ui.state.TopRateState
import com.example.film_app.viewModel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel){

    LaunchedEffect(viewModel.nowPlayingState) {
        viewModel.dataIntent.send(MainIntent.fetchNowPlaying)

        viewModel.nowPlayingState.collect{

            when (it) {
                is NowPlayingState.Idle -> {}

                is NowPlayingState.Loading -> {}

                is NowPlayingState.NowPlayingData -> { Log.v("getData" ,it.toString()) }

                is NowPlayingState.NowPlayingError -> {}
            }

        }
    }

    LaunchedEffect(viewModel.popularState) {
        viewModel.dataIntent.send(MainIntent.fetchPopular)

        viewModel.popularState.collect{

            when (it) {
                is PopularState.Idle -> {}

                is PopularState.Loading -> {}

                is PopularState.PopularData -> { Log.v("getData1" ,it.toString()) }

                is PopularState.PopularError -> {}
            }

        }

    }

    LaunchedEffect(viewModel.topRateState) {
        viewModel.dataIntent.send(MainIntent.fetchTopRate)

        viewModel.topRateState.collect{

            when (it) {
                is TopRateState.Idle -> {}

                is TopRateState.Loading -> {}

                is TopRateState.TopRateData -> { Log.v("getData2" ,it.toString()) }

                is TopRateState.TopRateError -> { Log.v("getDataError" ,it.toString()) }
            }

        }


    }



}