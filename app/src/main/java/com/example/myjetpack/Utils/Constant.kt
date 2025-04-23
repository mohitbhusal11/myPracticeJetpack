package com.example.myjetpack.Utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import com.example.myjetpack.dataModel.BottomNavigationModel

object Constants {
    val BottomNavItems = listOf(
        // Home screen
        BottomNavigationModel(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        // Search screen
        BottomNavigationModel(
            label = "Search",
            icon = Icons.Filled.Search,
            route = "search"
        ),
        // Profile screen
        BottomNavigationModel(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        ),
        // UI practice
        BottomNavigationModel(
            label = "practice",
            icon = Icons.Filled.Info,
            route = "practice"
        )
    )
}