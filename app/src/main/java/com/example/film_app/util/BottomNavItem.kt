package com.example.film_app.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val rout : String , val icon : ImageVector , val label : String) {

    object HomeScreen : BottomNavItem("HomeScreen", Icons.Outlined.Home, "Home")
    object SearchScreen : BottomNavItem("SearchScreen", Icons.Outlined.Search, "Search")
    object WatchListScreen : BottomNavItem("WatchListScreen", Icons.Outlined.FavoriteBorder, "Watch List")
    object DetailScreen : BottomNavItem("DetailScreen", Icons.Outlined.FavoriteBorder, "Detail")
    object FirstRunScreen : BottomNavItem("FirstRunScreen", Icons.Outlined.Add, "Intro")
    object SignInScreen : BottomNavItem("SignInScreen", Icons.Outlined.Add, "SignIn")
    object SignUpScreen : BottomNavItem("SignUpScreen", Icons.Outlined.Add, "SignUp")
    object SettingScreen : BottomNavItem("SettingScreen", Icons.Outlined.Add, "Setting")
}