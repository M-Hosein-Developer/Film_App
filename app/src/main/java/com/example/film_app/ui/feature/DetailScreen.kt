package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.ui.intent.DetailIntent
import com.example.film_app.ui.state.detailState.DetailState
import com.example.film_app.util.EMPTY_DATA
import com.example.film_app.viewModel.DetailViewModel

@Composable
fun DetailScreen(viewModel: DetailViewModel, navController: NavHostController, moviesId: Int) {

    var detailData by remember { mutableStateOf(EMPTY_DATA) }
    
    LaunchedEffect(1) {
        viewModel.dataIntent.send(DetailIntent.fetchAllData)

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
    
    

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailToolbar(
            { navController.popBackStack() },
            { }
        )

        DetailInfo(detailData)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(onBackCLicked: () -> Unit, onFavoriteClicked: () -> Unit) {

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
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onFavoriteClicked.invoke() }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
        )

}

@Composable
fun DetailInfo(detailData: AllDataEntity) {

    Column(
        Modifier.fillMaxSize()
    ) {

        Box(
            Modifier.height(355.dp)
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
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(8.dp),
                contentScale = ContentScale.Crop,
            )

        }



    }

}