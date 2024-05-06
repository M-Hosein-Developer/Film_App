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
import com.example.film_app.ui.feature.DetailScreen
import com.example.film_app.ui.feature.FirstRunScreen
import com.example.film_app.ui.feature.HomeScreen
import com.example.film_app.ui.feature.SearchScreen
import com.example.film_app.ui.feature.SignInScreen
import com.example.film_app.ui.feature.SignUpScreen
import com.example.film_app.ui.feature.WatchListScreen
import com.example.film_app.ui.theme.AppTheme
import com.example.film_app.util.BottomNavItem
import com.example.film_app.viewModel.DetailAndWatchListViewModel
import com.example.film_app.viewModel.HomeViewModel
import com.example.film_app.viewModel.RegisterViewModel
import com.example.film_app.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel : HomeViewModel by viewModels()
    private val detailAndWatchListViewModel : DetailAndWatchListViewModel by viewModels()
    private val searchViewModel : SearchViewModel by viewModels()
    private val registerViewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BottomBar(navController , homeViewModel , detailAndWatchListViewModel , searchViewModel , registerViewModel)
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
    detailAndWatchListViewModel: DetailAndWatchListViewModel,
    searchViewModel: SearchViewModel,
    registerViewModel: RegisterViewModel
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

        NavHost(navController = navController, startDestination = BottomNavItem.FirstRunScreen.rout){

            composable(BottomNavItem.HomeScreen.rout){
                HomeScreen(homeViewModel , navController)
                isVisible = true
            }

            composable(BottomNavItem.SearchScreen.rout){
                SearchScreen(searchViewModel , navController)
                isVisible = true
            }
            composable(BottomNavItem.WatchListScreen.rout){
                WatchListScreen(detailAndWatchListViewModel , navController)
                isVisible = true
            }

            composable(
                route = BottomNavItem.DetailScreen.rout + "/{DetailNav}",
                arguments = listOf(navArgument("DetailNav"){type = NavType.IntType})
                ){
                DetailScreen(detailAndWatchListViewModel , navController , it.arguments!!.getInt("DetailNav" , -1))
                isVisible = false
            }

            composable(BottomNavItem.FirstRunScreen.rout){
                FirstRunScreen(navController)
                isVisible = false
            }

            composable(BottomNavItem.SignInScreen.rout){
                SignInScreen(navController)
                isVisible = false
            }

            composable(BottomNavItem.SignUpScreen.rout){
                SignUpScreen(navController , registerViewModel)
                isVisible = false
            }

        }

    }

}



