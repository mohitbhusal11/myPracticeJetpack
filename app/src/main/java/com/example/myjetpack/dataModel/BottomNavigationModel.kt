package com.example.myjetpack.dataModel

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationModel(
    val label: String,
    val icon: ImageVector,
    val route:String,
)