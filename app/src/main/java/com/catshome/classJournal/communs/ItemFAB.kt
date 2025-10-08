package com.catshome.classJournal.communs



import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.R

data class ItemFAB(
    val modifier: Modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
    val label: String ="",
    val containerColor: Color,
    val contentColor: Color,
    val icon: Painter?,//Icons.Default.Add,
    val onClick: () -> Unit
)
