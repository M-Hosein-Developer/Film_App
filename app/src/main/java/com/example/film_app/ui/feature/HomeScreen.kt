package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.TrendEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import com.example.film_app.ui.intent.MainIntent
import com.example.film_app.ui.state.NowPlayingState
import com.example.film_app.ui.state.PopularState
import com.example.film_app.ui.state.TopRateState
import com.example.film_app.ui.state.TrendState
import com.example.film_app.ui.state.UpcomingState
import com.example.film_app.viewModel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel){

    val nowPlaying = remember { mutableStateOf(listOf<NowPlayingEntity>()) }
    val popular = remember { mutableStateOf(listOf<PopularEntity>()) }
    val topRate = remember { mutableStateOf(listOf<TopRatedEntity>()) }
    val upcoming = remember { mutableStateOf(listOf<UpcomingEntity>()) }
    val trend = remember { mutableStateOf(listOf<TrendEntity>()) }

    LaunchedEffect(viewModel.nowPlayingState) {
        viewModel.dataIntent.send(MainIntent.fetchNowPlaying)

        viewModel.nowPlayingState.collect{

            when (it) {
                is NowPlayingState.Idle -> {}

                is NowPlayingState.Loading -> {}

                is NowPlayingState.NowPlayingData -> { nowPlaying.value = it.nowPlying }

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

                is PopularState.PopularData -> { popular.value = it.popular }

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

                is TopRateState.TopRateData -> { topRate.value = it.topRate }

                is TopRateState.TopRateError -> { Log.v("getDataError" ,it.toString()) }
            }

        }


    }
    LaunchedEffect(viewModel.upcomingState) {
        viewModel.dataIntent.send(MainIntent.fetchUpcoming)

        viewModel.upcomingState.collect{

            when (it) {
                is UpcomingState.Idle -> {}

                is UpcomingState.Loading -> {}

                is UpcomingState.UpcomingData -> { upcoming.value = it.upcoming }

                is UpcomingState.UpcomingError -> { Log.v("getDataError" ,it.toString()) }
            }

        }


    }
    LaunchedEffect(viewModel.trendState) {
        viewModel.dataIntent.send(MainIntent.fetchTrend)

        viewModel.trendState.collect{

            when (it) {
                is TrendState.Idle -> {}

                is TrendState.Loading -> {}

                is TrendState.TrendData -> { trend.value = it.trend
                    Log.v("getData" ,it.toString())
                }

                is TrendState.TrendError -> { Log.v("getDataError" ,it.toString()) }
            }

        }


    }


    Column(
        Modifier.fillMaxSize()
    ) {

        Text(
            text = "What do you want to watch?",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 24.dp , top = 24.dp)
        )

        Trend(trend.value)

    }

}

@Composable
fun Trend(trendList: List<TrendEntity>) {

    LazyRow(
        Modifier.padding(start = 24.dp , end = 24.dp , top = 16.dp)
    ) {

        items(trendList.size){
            TrendItem(trendList[it])
        }

    }

}

@Composable
fun TrendItem(trend: TrendEntity) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .height(250.dp)
            .width(165.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + trend.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}