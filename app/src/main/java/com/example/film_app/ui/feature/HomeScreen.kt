package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.film_app.ui.intent.MainIntent
import com.example.film_app.ui.state.NowPlayingState
import com.example.film_app.viewModel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel){

    LaunchedEffect(1) {
        viewModel.dataIntent.send(MainIntent.fetchNowPlaying)

        viewModel.nowPlayingState.collect{

            when (it) {
                is NowPlayingState.Idle -> {

                }

                is NowPlayingState.Loading -> {

                }

                is NowPlayingState.NowPlayingData -> {
                    Log.v("nowPlayData" ,it.toString())
                }

                is NowPlayingState.NowPlayingError -> {

                }
            }

        }

    }

}