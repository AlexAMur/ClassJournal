package com.catshome.classJournal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.RevealState
import androidx.wear.compose.material.SwipeToRevealActionColors
import androidx.wear.compose.material.SwipeToRevealChip

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeReveal(
    revealState: RevealState,
    primaryAction: @Composable ()-> Unit,
    onFullSwipe: ()-> Unit,
    modifier: Modifier,
    secondaryAction:@Composable (()-> Unit)? = null,
    undoPrimaryAction: @Composable (()-> Unit)? = null,
    undoSecondaryAction:@Composable (()-> Unit)? = null,
    colors: SwipeToRevealActionColors,
    shape: Shape = MaterialTheme.shapes.small,
    content:@Composable ()-> Unit
) {
    SwipeToRevealChip(
        primaryAction = primaryAction,
        revealState = revealState,
        onFullSwipe = onFullSwipe,
        modifier = modifier,
        secondaryAction = secondaryAction,
        undoPrimaryAction = undoPrimaryAction,
        undoSecondaryAction = undoSecondaryAction,
        colors = colors,
        shape = shape ,
        content =content
    )
}
@Composable
fun deleteItem(onClick:(Unit)-> Unit){

}