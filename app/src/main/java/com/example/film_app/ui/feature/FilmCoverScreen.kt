package com.example.film_app.ui.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.ui.intent.FilmCoverIntent
import com.example.film_app.ui.state.filmCover.FilmCoverState
import com.example.film_app.util.EMPTY_DATA
import com.example.film_app.viewModel.FilmCoverViewModel
import kotlin.random.Random

@Composable
fun FilmCoverScreen(navController: NavHostController, viewModel: FilmCoverViewModel) {

    var filmCover by remember { mutableStateOf(listOf(EMPTY_DATA)) }

    var filmUrl by remember { mutableStateOf("") }
    var filmStateUrl by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.dataIntent.send(FilmCoverIntent.FetchFilmCover)

        viewModel.filmCoverState.collect{

            when(it){
                is FilmCoverState.Idle -> {}
                is FilmCoverState.FetchData -> { filmCover = it.data }
                is FilmCoverState.Error -> {}
            }

        }
    }


    Column(
        Modifier.fillMaxSize()
    ) {

        FilmCoverToolbar { navController.popBackStack() }

        if (filmStateUrl){
            FilmCoverClicked(filmCover , filmUrl){
                filmStateUrl = false
            }
        }else{
            FilmCover(filmCover) {
                filmUrl = it
                filmStateUrl = true
            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmCoverToolbar(onBackCLicked: () -> Unit) {

    TopAppBar(
        title = { Text(
            text = "Film Cover",
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) }


        ,
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun FilmCover(filmCover: List<AllDataEntity>, onCoverLicked: (String) -> Unit) {


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
        ) {

        items(filmCover.size){ it ->

            if (filmCover.size > 1)
                FilmCoverItem(
                    Random.nextInt(200, 370).dp,
                    filmCover[it]
                ) { onCoverLicked.invoke(it) }

        }

    }

}

@Composable
fun FilmCoverClicked(filmCover: List<AllDataEntity>, filmUrl: String , closeCoverClicked :() -> Unit) {

    Box(Modifier.fillMaxSize()){

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().blur(12.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {

            items(filmCover.size){

                if (filmCover.size > 1)
                    FilmCoverItem(
                        Random.nextInt(200, 370).dp,
                        filmCover[it]
                    ) { }

            }

        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .clickable { closeCoverClicked.invoke() },
            contentAlignment = Alignment.Center
        ){

            AsyncImage(
                model = filmUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .clip(RoundedCornerShape(14.dp)),
                alignment = Alignment.Center
            )

        }

    }

}

@Composable
fun FilmCoverItem(height: Dp, filmCover: AllDataEntity, onCoverLicked: (String) -> Unit) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onCoverLicked.invoke("https://image.tmdb.org/t/p/w500" + filmCover.posterPath) }
    ){

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + filmCover.posterPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }


}
