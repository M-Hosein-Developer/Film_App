package com.example.film_app.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
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

        Text(text = detailData.toString())

    }


}