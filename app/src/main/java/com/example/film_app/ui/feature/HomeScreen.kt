package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.film_app.R
import com.example.film_app.model.database.entities.NowPlayingEntity
import com.example.film_app.model.database.entities.PopularEntity
import com.example.film_app.model.database.entities.TopRatedEntity
import com.example.film_app.model.database.entities.TrendEntity
import com.example.film_app.model.database.entities.UpcomingEntity
import com.example.film_app.ui.intent.HomeIntent
import com.example.film_app.ui.state.homeState.NowPlayingState
import com.example.film_app.ui.state.homeState.PopularState
import com.example.film_app.ui.state.homeState.TopRateState
import com.example.film_app.ui.state.homeState.TrendState
import com.example.film_app.ui.state.homeState.UpcomingState
import com.example.film_app.util.BottomNavItem
import com.example.film_app.util.ButtonIdCategory
import com.example.film_app.viewModel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController){

    val nowPlaying = remember { mutableStateOf(listOf<NowPlayingEntity>()) }
    val popular = remember { mutableStateOf(listOf<PopularEntity>()) }
    val topRate = remember { mutableStateOf(listOf<TopRatedEntity>()) }
    val upcoming = remember { mutableStateOf(listOf<UpcomingEntity>()) }
    val trend = remember { mutableStateOf(listOf<TrendEntity>()) }

    LaunchedEffect(viewModel.nowPlayingState) {
        viewModel.dataIntent.send(HomeIntent.FetchNowPlaying)

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
        viewModel.dataIntent.send(HomeIntent.FetchPopular)

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
        viewModel.dataIntent.send(HomeIntent.FetchTopRate)

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
        viewModel.dataIntent.send(HomeIntent.FetchUpcoming)

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
        viewModel.dataIntent.send(HomeIntent.FetchTrend)

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

        Trend(trend.value){
            navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it)
        }


        SetDataByButtons(
            nowPlaying.value , popular.value , topRate.value , upcoming.value,
            { navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it) },
            { navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it) },
            { navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it) },
            { navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it) }
        )



    }

}

@Composable
fun Trend(trendList: List<TrendEntity> , onItemTrendClick :(Int) -> Unit) {

    if (trendList.isNotEmpty()) {

        LazyRow(
            Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
        ) {

            items(trendList.size) { it ->

                TrendItem(trendList[it]) { onItemTrendClick.invoke(it) }
            }

        }

    } else{
        Loading()
    }

}

@Composable
fun TrendItem(trend: TrendEntity , onItemTrendClick :(Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .height(250.dp)
            .width(165.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemTrendClick.invoke(trend.id) }
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + trend.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}


@Composable
fun SetDataByButtons(
    nowPlying: List<NowPlayingEntity>,
    popular: List<PopularEntity>,
    topRate: List<TopRatedEntity>,
    upcoming: List<UpcomingEntity>,
    onItemNowPlayingClick :(Int) -> Unit,
    onItemPopularClick :(Int) -> Unit,
    onItemTopRateClick :(Int) -> Unit,
    onItemUpcomingClick :(Int) -> Unit
) {

    var clickedButton by remember { mutableStateOf<ButtonIdCategory?>(null) }

    Column {

        Row(
            Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp)
                .horizontalScroll(rememberScrollState())
                .clip(RoundedCornerShape(12.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            Button(
                onClick = { clickedButton = ButtonIdCategory.BUTTON_1 },
                shape = RectangleShape
            ) {
                Text(text = "NOW PLAYING")
            }

            Button(
                onClick = { clickedButton = ButtonIdCategory.BUTTON_2 },
                shape = RectangleShape
            ) {
                Text(text = "POPULAR")
            }

            Button(
                onClick = { clickedButton = ButtonIdCategory.BUTTON_3 },
                shape = RectangleShape
            ) {
                Text(text = "TOP RATED")
            }


            Button(
                onClick = { clickedButton = ButtonIdCategory.BUTTON_4 },
                shape = RectangleShape
            ) {
                Text(text = "UPCOMING")
            }

        }

    }


    when (clickedButton) {
        ButtonIdCategory.BUTTON_1 -> {
            if (nowPlying.isNotEmpty())
            FilmCategoryNowPlaying(nowPlying){onItemNowPlayingClick.invoke(it)}
            else Loading()

        }
        ButtonIdCategory.BUTTON_2 -> {
            if (popular.isNotEmpty())
            FilmCategoryPopular(popular){onItemPopularClick.invoke(it)}
            else Loading()
        }
        ButtonIdCategory.BUTTON_3 -> {
            if (topRate.isNotEmpty())
            FilmCategoryTopRate(topRate){onItemTopRateClick.invoke(it)}
            else Loading()
        }
        ButtonIdCategory.BUTTON_4 -> {
            if (upcoming.isNotEmpty())
            FilmCategoryUpcoming(upcoming){onItemUpcomingClick.invoke(it)}
            else Loading()
        }
        else -> {
            if (nowPlying.isNotEmpty())
                FilmCategoryNowPlaying(nowPlying){onItemNowPlayingClick.invoke(it)}
            else Loading()
        }
    }

}


//Now playing lazy
@Composable
fun FilmCategoryNowPlaying(nowPlaying: List<NowPlayingEntity> , onItemNowPlayingClick :(Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 82.dp)
    ) {

        items(nowPlaying.size) { it ->
            FilmCategoryNowPlayingItem(nowPlaying[it]){ onItemNowPlayingClick.invoke(it) }
        }

    }

}

@Composable
fun FilmCategoryNowPlayingItem(nowPlaying: NowPlayingEntity , onItemNowPlayingClick :(Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp, top = 12.dp)
            .height(200.dp)
            .width(125.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemNowPlayingClick.invoke(nowPlaying.id) }
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + nowPlaying.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}

//Popular lazy
@Composable
fun FilmCategoryPopular(popular: List<PopularEntity> , onItemPopularClick :(Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 82.dp)
    ) {

        items(popular.size) { it ->
            FilmCategoryTopRateItem(popular[it]){ onItemPopularClick.invoke(it) }
        }

    }

}

@Composable
fun FilmCategoryTopRateItem(popular: PopularEntity, onItemPopularClick :(Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp, top = 12.dp)
            .height(200.dp)
            .width(125.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemPopularClick.invoke(popular.id) }
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + popular.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}

//Top rate lazy
@Composable
fun FilmCategoryTopRate(topRate: List<TopRatedEntity> , onItemTopRateClick :(Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 82.dp)
    ) {

        items(topRate.size) { it ->
            FilmCategoryTopRateItem(topRate[it]){ onItemTopRateClick.invoke(it) }
        }

    }

}

@Composable
fun FilmCategoryTopRateItem(topRate: TopRatedEntity , onItemTopRateClick :(Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp, top = 12.dp)
            .height(200.dp)
            .width(125.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemTopRateClick.invoke(topRate.id) }
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + topRate.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}

//Upcoming lazy
@Composable
fun FilmCategoryUpcoming(upcoming: List<UpcomingEntity> , onItemUpcomingClick :(Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 82.dp)
    ) {

        items(upcoming.size) { it ->
            FilmCategoryTopRateItem(upcoming[it]){ onItemUpcomingClick.invoke(it) }
        }

    }

}

@Composable
fun FilmCategoryTopRateItem(upcoming: UpcomingEntity , onItemUpcomingClick :(Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(end = 12.dp, top = 12.dp)
            .height(200.dp)
            .width(125.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemUpcomingClick.invoke(upcoming.id) }
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + upcoming.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }

}

@Composable
fun Loading() {


    Column(
        Modifier.fillMaxWidth().height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loading)
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(100.dp)
        )

    }


}
