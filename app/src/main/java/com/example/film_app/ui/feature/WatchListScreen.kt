package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.R
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.WatchListState
import com.example.film_app.util.BottomNavItem
import com.example.film_app.util.EMPTY_DATA1
import com.example.film_app.viewModel.DetailAndWatchListViewModel

@Composable
fun WatchListScreen(viewModel: DetailAndWatchListViewModel, navController: NavHostController) {

    var watchListData by remember { mutableStateOf(listOf(EMPTY_DATA1)) }

    LaunchedEffect(watchListData) {
        viewModel.dataIntent.send(DetailAndWatchListIntent.FetchWatchListId(EMPTY_DATA1))

        viewModel.watchListState.collect{
            when(it){

                is WatchListState.Idle -> {}
                is WatchListState.Loading -> {}
                is WatchListState.WatchList -> {
                    watchListData = it.watchList
                    Log.v("testData1" , it.watchList.toString())

                }
                is WatchListState.WatchListError -> {}

            }


        }

    }


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WatchListToolbar { navController.popBackStack() }

        if(watchListData.isNotEmpty()) {

            WatchListLazy(watchListData) {
                navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it)
            }

        }else {
            EmptyList()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListToolbar(onBackCLicked: () -> Unit) {

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
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun WatchListLazy(watchList: List<WatchListEntity>, onItemClick: (Int) -> Unit) {

    LazyColumn(
        Modifier.fillMaxSize()
            .padding(bottom = 70.dp)
    ) {

        items(watchList.size) { it ->
            WatchListLazyItem(watchList[it]) { onItemClick.invoke(it) }
        }

    }

}

@Composable
fun WatchListLazyItem(watchList: WatchListEntity, onItemClick: (Int) -> Unit) {


    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 24.dp)
            .padding(vertical = 12.dp)
            .clickable { onItemClick.invoke(watchList.id) }

    ) {


        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + watchList.posterPath,
            contentDescription = null,
            modifier = Modifier
                .width(110.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight(),
        ) {

            Text(
                text = watchList.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
              verticalAlignment = Alignment.CenterVertically
            ){

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    Modifier.padding(end = 6.dp)
                )

                Text(
                    text = watchList.voteAverage.toString(),
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    Modifier.padding(end = 6.dp)
                )

                Text(
                    text = watchList.releaseDate,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    Modifier.padding(end = 6.dp)
                )

                Text(
                    text = watchList.originalLanguage,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }


        }


    }

}

@Composable
fun EmptyList(){

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            model = R.drawable.empty ,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
            )
        
        Text(
            text = "There is no movie yet!",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Find your movie by Type title, categories, years, etc ",
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(top = 12.dp)
        )

    }

}