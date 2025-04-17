package com.catshome.classjornal.Screens

import androidx.compose.ui.graphics.vector.ImageVector

data class ItemBottomBar(
    val label: String,
    val route: String,
    val icon: ImageVector,
    val iconUnselect: ImageVector,
    val onClick:  Unit
)
