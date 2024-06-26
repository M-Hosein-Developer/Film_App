package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.model.dataClass.TrailerResponse
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.DeleteFromWatchListState
import com.example.film_app.ui.state.detailState.DetailState
import com.example.film_app.ui.state.detailState.TrailerState
import com.example.film_app.ui.state.detailState.WatchListState
import com.example.film_app.util.ButtonIdDetail
import com.example.film_app.util.EMPTY_DATA
import com.example.film_app.util.EMPTY_DATA1
import com.example.film_app.util.TRAILER_EMPTY_DATA
import com.example.film_app.viewModel.DetailAndWatchListViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun DetailScreen(viewModel: DetailAndWatchListViewModel, navController: NavHostController, moviesId: Int) {

    var detailData by remember { mutableStateOf(EMPTY_DATA) }

    var watchListData by remember { mutableStateOf(listOf(EMPTY_DATA1)) }
    var addFilmId by remember { mutableStateOf(EMPTY_DATA1) }
    var removeFilmId by remember { mutableStateOf(EMPTY_DATA1) }

    var trailerId by remember { mutableIntStateOf(-1) }
    var trailerData by remember { mutableStateOf(listOf(TRAILER_EMPTY_DATA)) }

    LaunchedEffect(1) {
        viewModel.dataIntent.send(DetailAndWatchListIntent.FetchAllData)

        viewModel.allDataState.collect{ it ->

            when(it){

                is DetailState.Idle -> {  }
                is DetailState.Loading -> {  }
                is DetailState.AllData -> { 
                    
                    it.allData.forEach {
                        
                        if (it.id == moviesId){
                            Log.v("getDataDetail" , moviesId.toString())
                            detailData = it
                        }
                        
                    }
                    
                }
                is DetailState.AllDataError -> {
                    Log.v("getDataDetailError" , it.allDataError.toString())
                }

            }

        }

    }

    LaunchedEffect(addFilmId) {
        viewModel.dataIntent.send(
            DetailAndWatchListIntent.FetchWatchListId(addFilmId
            )
        )

        viewModel.watchListState.collect{
            when(it){

                is WatchListState.Idle -> {}
                is WatchListState.Loading -> {}
                is WatchListState.WatchList -> { watchListData = it.watchList }
                is WatchListState.WatchListError -> {}

            }
        }
    }

    LaunchedEffect(removeFilmId) {
        viewModel.dataIntent.send(DetailAndWatchListIntent.DeleteWatchListId(removeFilmId))

        viewModel.deleteFilmFromWatchListState.collect {
            when (it) {

                is DeleteFromWatchListState.Idle -> {}
                is DeleteFromWatchListState.Loading -> {}
                is DeleteFromWatchListState.DeleteWatchListError -> {}

            }
        }

    }

    LaunchedEffect(trailerId) {
        viewModel.dataIntent.send(DetailAndWatchListIntent.FetchTrailerById(trailerId))

        viewModel.trailerState.collect {

            when (it) {

                is TrailerState.Idle -> {}
                is TrailerState.Loading -> {}
                is TrailerState.TrailerDataById -> {
                    trailerData = it.trailerData
                }

                is TrailerState.TrailerError -> {}

            }

        }

    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailToolbar(
            detailData = detailData,
            watchListData = watchListData,
            onBackCLicked = { navController.popBackStack() },

            onAddFavoriteClicked = {
                addFilmId = it
            },

            onDeleteFavoriteClicked = {
                removeFilmId = it
            }
        )

        DetailInfo(detailData, trailerData){ trailerId = it }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(
    detailData: AllDataEntity,
    watchListData : List<WatchListEntity>,
    onBackCLicked: () -> Unit,
    onAddFavoriteClicked: (WatchListEntity) -> Unit,
    onDeleteFavoriteClicked: (WatchListEntity) -> Unit
) {

    val favoriteBtn = remember { mutableStateOf(false) }
    var favoriteBtnDb = false

    TopAppBar(
        title = { Text(
            text = "Detail",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {

                favoriteBtn.value = !favoriteBtn.value

                if (favoriteBtn.value) {

                    onAddFavoriteClicked.invoke(
                        WatchListEntity(
                            detailData.adult,
                            detailData.backdropPath,
                            detailData.id,
                            detailData.originalLanguage,
                            detailData.originalTitle,
                            detailData.overview,
                            detailData.popularity,
                            detailData.posterPath,
                            detailData.releaseDate,
                            detailData.title,
                            detailData.video,
                            detailData.voteAverage,
                            detailData.voteCount
                        )
                    )
                }else {
                    onDeleteFavoriteClicked.invoke(
                        WatchListEntity(
                            detailData.adult,
                            detailData.backdropPath,
                            detailData.id,
                            detailData.originalLanguage,
                            detailData.originalTitle,
                            detailData.overview,
                            detailData.popularity,
                            detailData.posterPath,
                            detailData.releaseDate,
                            detailData.title,
                            detailData.video,
                            detailData.voteAverage,
                            detailData.voteCount
                        )
                    )
                }

            }) {

                watchListData.forEach {
                    if (detailData.id == it.id) {
                        favoriteBtnDb = true
                    }
                }

                if (favoriteBtn.value)
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                else if (favoriteBtnDb)
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                else
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)

            }
        },
        modifier = Modifier.fillMaxWidth()
        )

}

@Composable
fun DetailInfo(detailData: AllDataEntity, trailerData: List<TrailerResponse.MoviesResult>, onTrailerClick: (Int) -> Unit) {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        CoverDetail(detailData)

        DescriptionFilm(detailData)

        ChangeAboutScreenByButton(detailData, trailerData){ onTrailerClick.invoke(it) }
    }

}


@Composable
fun CoverDetail(detailData: AllDataEntity) {

    Box(
        Modifier
            .height(355.dp)
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + detailData.backdropPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + detailData.posterPath,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 40.dp)
                .width(110.dp)
                .height(200.dp)
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp),
            contentScale = ContentScale.Crop,
        )


        Text(
            text = detailData.title,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 50.dp, start = 170.dp, end = 14.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1
        )

    }

}

@Composable
fun DescriptionFilm(detailData: AllDataEntity) {

    Row (
        Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){


        Row(
            Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                Modifier.padding(end = 6.dp)
            )

            Text(
                text = detailData.releaseDate,
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()  //fill the max height
                .width(1.dp)
                .padding(vertical = 24.dp)
        )

        Row(
            Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                Modifier.padding(end = 6.dp)
            )

            Text(
                text = detailData.voteAverage.toString(),
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()  //fill the max height
                .width(1.dp)
                .padding(vertical = 24.dp)
        )

        Row(
            Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null,
                Modifier.padding(end = 6.dp)
            )

            Text(
                text = detailData.voteCount.toString(),
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }

    }

}

@Composable
fun ChangeAboutScreenByButton(
    detailData: AllDataEntity,
    trailerData: List<TrailerResponse.MoviesResult>,
    onTrailerClick: (Int) -> Unit
) {

    var clickedButton by remember { mutableStateOf<ButtonIdDetail?>(null) }

    Column(
        Modifier.fillMaxSize()
    ){

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 38.dp)
                .clip(RoundedCornerShape(12.dp)),
        ) {

            Button(
                onClick = { clickedButton = ButtonIdDetail.BUTTON_1 },
                shape = RectangleShape,
                modifier = Modifier.weight(0.5f)
            ) {
                Text(text = "OVERVIEW")
            }

            Button(
                onClick = {
                    clickedButton = ButtonIdDetail.BUTTON_2
                    onTrailerClick.invoke(detailData.id)
                },
                shape = RectangleShape,
                modifier = Modifier.weight(0.5f)
            ) {
                Text(text = "TRAILER")
            }

        }

    }

    when (clickedButton) {
        ButtonIdDetail.BUTTON_1 -> {
            AboutFilm(detailData)
        }

        ButtonIdDetail.BUTTON_2 -> {
            if (trailerData[0].id != "")
            Trailer(trailerData)
        }

        else -> AboutFilm(detailData)
    }

}

@Composable
fun AboutFilm(detailData: AllDataEntity) {


    Column(
        Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 12.dp)
    ) {

        Text(
            text = "OVERVIEW",
            Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )

        Text(
            text = detailData.overview,
            Modifier.fillMaxWidth()
        )

    }


}

@Composable
fun Trailer(trailerData: List<TrailerResponse.MoviesResult>) {

    val lifecycleOwner = LocalLifecycleOwner.current

    Column {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
                .clip(RoundedCornerShape(16.dp)),
            factory = {context ->

            YouTubePlayerView(context).apply {

                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

                    override fun onReady(youTubePlayer: YouTubePlayer) {

                        youTubePlayer.loadVideo(trailerData[0].key , 0f)
                        youTubePlayer.play()
                        youTubePlayer.unMute()
                    }

                })

            }

        })

    }

}
