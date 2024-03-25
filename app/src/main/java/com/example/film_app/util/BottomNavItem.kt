package com.example.film_app.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val rout : String , val icon : ImageVector , val label : String) {

    object HomeScreen : BottomNavItem("HomeScreen", Icons.Filled.Home, "Home")
    object SearchScreen : BottomNavItem("SearchScreen", Icons.Filled.Search, "Search")

}