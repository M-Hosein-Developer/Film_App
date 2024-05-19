package com.example.film_app.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.random.Random

@Composable
fun FilmCoverScreen(navController: NavHostController) {

    Column(
        Modifier.fillMaxSize()
    ) {

        FilmCoverToolbar { navController.popBackStack() }

        FilmCover()

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
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun FilmCover(){



    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
        ) {

        items(10){

            FilmCoverItem(Random.nextInt(200 ,350).dp)

        }

    }


}

@Composable
fun FilmCoverItem(height: Dp){


    Box(
        modifier = Modifier.fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Red)
    ){



    }
   

}
