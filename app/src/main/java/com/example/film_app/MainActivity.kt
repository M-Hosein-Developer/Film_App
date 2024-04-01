package com.example.film_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.AppTheme
import com.example.film_app.ui.feature.DetailScreen
import com.example.film_app.ui.feature.HomeScreen
import com.example.film_app.ui.feature.SearchScreen
import com.example.film_app.ui.feature.WatchListScreen
import com.example.film_app.util.BottomNavItem
import com.example.film_app.viewModel.DetailAndWatchListViewModel
import com.example.film_app.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel : HomeViewModel by viewModels()
    private val detailAndWatchListViewModel : DetailAndWatchListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BottomBar(navController , homeViewModel , detailAndWatchListViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBar(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    detailAndWatchListViewModel: DetailAndWatchListViewModel
) {

    var isVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (isVisible) {
                NavigationBar {

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(BottomNavItem.SearchScreen.rout) },
                        icon = {
                            Icon(
                                imageVector = BottomNavItem.SearchScreen.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = BottomNavItem.SearchScreen.label)
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(BottomNavItem.HomeScreen.rout) },
                        icon = {
                            Icon(
                                imageVector = BottomNavItem.HomeScreen.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = BottomNavItem.HomeScreen.label)
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(BottomNavItem.WatchListScreen.rout) },
                        icon = {
                            Icon(
                                imageVector = BottomNavItem.WatchListScreen.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = BottomNavItem.WatchListScreen.label)
                        }
                    )

                }
            }
        }
    ) {

        NavHost(navController = navController, startDestination = BottomNavItem.HomeScreen.rout){

            composable(BottomNavItem.HomeScreen.rout){
                HomeScreen(homeViewModel , navController)
                isVisible = true
            }

            composable(BottomNavItem.SearchScreen.rout){
                SearchScreen()
                isVisible = true
            }
            composable(BottomNavItem.WatchListScreen.rout){
                WatchListScreen(detailAndWatchListViewModel)
                isVisible = true
            }

            composable(
                route = BottomNavItem.DetailScreen.rout + "/{DetailNav}",
                arguments = listOf(navArgument("DetailNav"){type = NavType.IntType})
                ){
                DetailScreen(detailAndWatchListViewModel , navController , it.arguments!!.getInt("DetailNav" , -1))
                isVisible = false
            }

        }

    }

}



