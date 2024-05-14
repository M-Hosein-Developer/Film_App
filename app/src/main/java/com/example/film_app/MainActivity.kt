package com.example.film_app

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.film_app.ui.feature.DetailScreen
import com.example.film_app.ui.feature.FirstRunScreen
import com.example.film_app.ui.feature.HomeScreen
import com.example.film_app.ui.feature.SearchScreen
import com.example.film_app.ui.feature.SettingScreen
import com.example.film_app.ui.feature.SignInScreen
import com.example.film_app.ui.feature.SignUpScreen
import com.example.film_app.ui.feature.WatchListScreen
import com.example.film_app.ui.state.settingState.DynamicThemeState
import com.example.film_app.ui.theme.AppTheme
import com.example.film_app.util.BottomNavItem
import com.example.film_app.viewModel.DetailAndWatchListViewModel
import com.example.film_app.viewModel.HomeViewModel
import com.example.film_app.viewModel.RegisterViewModel
import com.example.film_app.viewModel.SearchViewModel
import com.example.film_app.viewModel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel : HomeViewModel by viewModels()
    private val detailAndWatchListViewModel : DetailAndWatchListViewModel by viewModels()
    private val searchViewModel : SearchViewModel by viewModels()
    private val registerViewModel : RegisterViewModel by viewModels()
    private val settingViewModel : SettingViewModel by viewModels()
    @Inject lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var themeState by mutableStateOf(false)

        lifecycleScope.launch {
                settingViewModel.dynamicThemeState.collect {
                    when (it) {
                        is DynamicThemeState.Idle -> {}
                        is DynamicThemeState.ThemeState -> {
                            themeState = it.state
                        }
                    }

            }
        }

        setContent {

            val navController = rememberNavController()

            AppTheme(dynamicColor = themeState) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BottomBar(navController , homeViewModel , detailAndWatchListViewModel , searchViewModel , registerViewModel , sharedPref , settingViewModel)
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
    registerViewModel: RegisterViewModel,
    sharedPref: SharedPreferences,
    settingViewModel: SettingViewModel
) {

    var isVisible by remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {

                        AsyncImage(
                            model = R.drawable.popcorn,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .matchParentSize()
                                .size(200.dp)
                                .blur(12.dp)
                            )

                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(start = 18.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                text = "Film Center",
                                style = TextStyle(
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Text(
                                text = "Watch trailer and choose your favorite film",
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(top = 12.dp, bottom = 48.dp)
                            )
                        }

                    }

                }

                Divider()

                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
                    onClick = {
                        navController.navigate(BottomNavItem.HomeScreen.rout)
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Setting") },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    onClick = {
                        navController.navigate(BottomNavItem.SettingScreen.rout)
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Info") },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = null) },
                    onClick = {
                        navController.navigate(BottomNavItem.HomeScreen.rout)
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "FAQ") },
                    selected = false,
                    icon = { Icon(painter = painterResource(R.drawable.outline_question_mark_24), contentDescription = null) },
                    onClick = {
                        navController.navigate(BottomNavItem.HomeScreen.rout)
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )

            }
        }
    ) {

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

            NavHost(
                navController = navController,
                startDestination = if (sharedPref.getString(
                        "signIn",
                        "null"
                    ) == "successful"
                ) BottomNavItem.HomeScreen.rout else BottomNavItem.FirstRunScreen.rout
            ) {


                composable(BottomNavItem.HomeScreen.rout) {
                    HomeScreen(homeViewModel, navController)
                    isVisible = true
                }

                composable(BottomNavItem.SearchScreen.rout) {
                    SearchScreen(searchViewModel, navController)
                    isVisible = true
                }
                composable(BottomNavItem.WatchListScreen.rout) {
                    WatchListScreen(detailAndWatchListViewModel, navController)
                    isVisible = true
                }

                composable(
                    route = BottomNavItem.DetailScreen.rout + "/{DetailNav}",
                    arguments = listOf(navArgument("DetailNav") { type = NavType.IntType })
                ) {
                    DetailScreen(
                        detailAndWatchListViewModel,
                        navController,
                        it.arguments!!.getInt("DetailNav", -1)
                    )
                    isVisible = false
                }

                composable(BottomNavItem.FirstRunScreen.rout) {
                    FirstRunScreen(navController)
                    isVisible = false
                }

                composable(BottomNavItem.SignInScreen.rout) {
                    SignInScreen(navController, registerViewModel)
                    isVisible = false
                }

                composable(BottomNavItem.SignUpScreen.rout) {
                    SignUpScreen(navController, registerViewModel)
                    isVisible = false
                }

                composable(BottomNavItem.SettingScreen.rout) {
                    SettingScreen(navController , settingViewModel)
                    isVisible = false
                }

            }

        }

    }

}



