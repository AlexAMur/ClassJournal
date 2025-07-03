package com.catshome.classJournal.screens

import androidx.compose.ui.graphics.vector.ImageVector
//class opisaniya punktov menu navigation bar
data class ItemBottomBar(
    val label: String,
    val route: String,
    val icon: ImageVector,
    val iconUnselect: ImageVector,
    val onClick:  Unit
)
