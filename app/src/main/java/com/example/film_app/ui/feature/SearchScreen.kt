package com.example.film_app.ui.feature

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.R
import com.example.film_app.model.database.entities.AllDataEntity
import com.example.film_app.ui.intent.SearchIntent
import com.example.film_app.ui.state.searchState.SearchState
import com.example.film_app.util.BottomNavItem
import com.example.film_app.util.EMPTY_DATA
import com.example.film_app.viewModel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {

    var searchText by remember { mutableStateOf("") }
    var dataBySearch by remember { mutableStateOf(listOf(EMPTY_DATA)) }

    LaunchedEffect(searchText) {

        viewModel.dataIntent.send(SearchIntent.FetchDataBySearch(

            if (searchText == ""){
                null
            }else{
                searchText
            }

        ))

        viewModel.getDataBySearch.collect{

            when(it){

                is SearchState.Idle -> {}
                is SearchState.Loading ->{}
                is SearchState.GetDataBySearch -> {dataBySearch = it.dataBySearch}
                is SearchState.Error -> {}

            }

        }

    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchToolbar { navController.popBackStack() }

        SearchBox(
            edtValue = searchText
            , icon = Icons.Default.Search
            , hint = "Search"
        ) {
            searchText = it
        }

        if(dataBySearch.isNotEmpty()) {

            SearchResult(dataBySearch){
                navController.navigate(BottomNavItem.DetailScreen.rout + "/" + it)
            }

        }else {
            EmptyListSearch()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchToolbar(onBackCLicked: () -> Unit) {

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
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun SearchBox(edtValue: String, icon: ImageVector, hint: String, onValueChanges: (String) -> Unit) {
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        )

}

@Composable
fun SearchResult(dataBySearch: List<AllDataEntity> , onItemClick: (Int) -> Unit) {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {

        items(dataBySearch.size){ it ->
            SearchResultItem(dataBySearch[it]) { onItemClick.invoke(it) }
        }

    }

}

@Composable
fun SearchResultItem(dataBySearch : AllDataEntity , onItemClick: (Int) -> Unit) {


    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 24.dp)
            .padding(vertical = 12.dp)
            .clickable { onItemClick.invoke(dataBySearch.id) }

    ) {


        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + dataBySearch.posterPath,
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
                text = dataBySearch.title,
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
                    text = dataBySearch.voteAverage.toString(),
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
                    text = dataBySearch.releaseDate,
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
                    text = dataBySearch.originalLanguage,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }


        }

    }

}

@Composable
fun EmptyListSearch(){

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            model = R.drawable.search ,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = "we are sorry, we can not find the movie :(",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Find your movie by Type title, categories, years, etc",
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(top = 12.dp)
        )

    }

}