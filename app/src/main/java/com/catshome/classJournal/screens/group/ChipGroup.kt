package com.catshome.classJournal.screens.group

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun SwipeToDismissListItems(
    onEndToStart: () -> Unit = {},

    content: @Composable (RowScope.()->Unit)) {
    val dismissState = rememberSwipeToDismissBoxState()
    var isVisible by remember { mutableStateOf(true) }
    if (isVisible) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val color by
                animateColorAsState(
                    when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> Color.LightGray
                        SwipeToDismissBoxValue.StartToEnd -> Color.Green
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                    }
                )
                Box(Modifier
                    .fillMaxSize()
                    .background(color))
            },
            content =content
            )
    }
}


//@OptIn(ExperimentalWearMaterialApi::class, ExperimentalWearFoundationApi::class)
//@Composable
//fun ChipGroup(
//    viewModel: GroupViewModel,
//    index: Int,
//    content: @Composable (() -> Unit)
//) {
//    //TODO Тестирование удаления  и востановления функций
//    val viewState by viewModel.viewState().collectAsState()
//    if (viewState.listItems[index].revealState == null) {
//        viewState.listItems[index].revealState = rememberRevealState()
//    }
//    if (viewState.listItems[index].swipeToDismissBoxState == null)
//        viewState.listItems[index].swipeToDismissBoxState = rememberSwipeToDismissBoxState{
//            Log.e ("CLJR", "SwperBOX")
//            true
//        }
//    SwipeToRevealChip(
//        revealState = viewState.listItems[index].revealState!!,
//        modifier = Modifier.edgeSwipeToDismiss(swipeToDismissBoxState = viewState.listItems[index].swipeToDismissBoxState!!),
//        primaryAction = {
//            SwipeToRevealPrimaryAction(
//                revealState = viewState.listItems[index].revealState!!,
//                icon = {
//                    Icon(
//                        SwipeToRevealDefaults.Delete,
//                        contentDescription = "Delete"
//                    )
//                },
//                label = {
//                    Text(
//                        "Delete",
//                        color = ClassJournalTheme.colors.primaryText
//                    )
//                },
//                onClick = {
//                    viewModel.obtainEvent(
//                        GroupEvent.DeleteClicked(
//                            viewState.listItems[index].group.uid,
//                            index
//                        )
//                    )
//                }
//            )
//        },
//        undoPrimaryAction = {
//            SwipeToRevealUndoAction(
//                revealState = viewState.listItems[index].revealState!!,
//                label = {
//                    Text(
//                        "Undo",
//                        color = ClassJournalTheme.colors.primaryText
//                    )
//                },
//                onClick = {
//                    viewModel.obtainEvent(
//                        GroupEvent.UndoDeleteClicked(
//                            viewState.listItems[index].group.uid,
//                            index
//                        )
//                    )
//                    Log.e("CLJR", "UNDO SWIPE")
////                    viewState.listItems[index].revealState = state
//                    CoroutineScope(Dispatchers.Main).launch {
////                        viewState.listItems[index].revealState?.snapTo(RevealValue.RightRevealing)
//                        viewState.listItems[index].revealState?.snapTo(RevealValue.LeftRevealing)
//                    }
//                }
//            )
//        },
//        onFullSwipe = {
//            CoroutineScope(Dispatchers.Default).launch {
//                delay(1000L)
//            }
//            if (viewState.isDelete)
//                viewModel.obtainEvent(
//                    GroupEvent.DeleteClicked(
//                        viewState.listItems[index].group.uid,
//                        index
//                    )
//                )
//
//            CoroutineScope(Dispatchers.Main).launch {
//                viewState.listItems[index].revealState?.snapTo(RevealValue.RightRevealed)
//            }
//        },
//        content = content
//    )
//}
