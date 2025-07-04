package com.catshome.classJournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.edgeSwipeToDismiss
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealValue
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealDefaults
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.SwipeToRevealUndoAction
import androidx.wear.compose.material.rememberRevealState
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.screens.viewModels.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ChipGroup(viewModel: GroupViewModel, index: Int, content: @Composable () -> Unit) {


    val viewState by viewModel.viewState().collectAsState()
    val revealState = rememberRevealState()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    SwipeToRevealChip(
        revealState = revealState,
        modifier = Modifier.edgeSwipeToDismiss(swipeToDismissBoxState = swipeToDismissBoxState),
        primaryAction = {
            SwipeToRevealPrimaryAction(
                revealState = revealState,
                icon = {
                    Icon(
                        SwipeToRevealDefaults.Delete,
                        contentDescription = "Delete"
                    )
                },
                label = {
                    Text(
                        "Delete",
                        color = ClassJournalTheme.colors.primaryText
                    )
                },
                onClick =  {    viewState.isDelete = true
                    CoroutineScope(Dispatchers.IO).launch {
                        revealState.snapTo(RevealValue.RightRevealed)
                        delay(4000L)
                        if (viewState.isDelete) {
                            viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.listGroup[index].uid))
                            revealState.snapTo(RevealValue.Covered)
                        }
                    }
                }
            )
        },
        undoPrimaryAction = {
            SwipeToRevealUndoAction(
                modifier = Modifier.background(ClassJournalTheme.colors.controlColor),
                revealState = revealState,
                label = { Text("Undo") },
                onClick = {
                    viewModel.obtainEvent(
                        GroupEvent.UndoDeleteClicked(
                            viewState.listGroup[index].uid
                        )
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        revealState.snapTo(RevealValue.Covered)
                    }
                }
            )
        },
        onFullSwipe = {
            viewState.isDelete = true
            CoroutineScope(Dispatchers.IO).launch {
                revealState.snapTo(RevealValue.RightRevealed)
                delay(4000L)
                if (viewState.isDelete) {
                    viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.listGroup[index].uid))
                    revealState.snapTo(RevealValue.Covered)
                }
            }
        },
        content = content
    )
}

