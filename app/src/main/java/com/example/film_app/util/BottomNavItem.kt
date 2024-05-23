package com.example.film_app.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val rout : String , val icon : ImageVector , val label : String) {

    data object HomeScreen : BottomNavItem("HomeScreen", Icons.Outlined.Home, "Home")
    data object SearchScreen : BottomNavItem("SearchScreen", Icons.Outlined.Search, "Search")
    data object WatchListScreen : BottomNavItem("WatchListScreen", Icons.Outlined.FavoriteBorder, "Watch List")
    data object DetailScreen : BottomNavItem("DetailScreen", Icons.Outlined.FavoriteBorder, "Detail")
    data object FirstRunScreen : BottomNavItem("FirstRunScreen", Icons.Outlined.Add, "Intro")
    data object SignInScreen : BottomNavItem("SignInScreen", Icons.Outlined.Add, "SignIn")
    data object SignUpScreen : BottomNavItem("SignUpScreen", Icons.Outlined.Add, "SignUp")
    data object SettingScreen : BottomNavItem("SettingScreen", Icons.Outlined.Add, "Setting")
    data object InfoScreen : BottomNavItem("InfoScreen", Icons.Outlined.Add, "Info")
    data object FilmCoverScreen : BottomNavItem("FilmCoverScreen", Icons.Outlined.Add, "FilmCoverInfo")
}