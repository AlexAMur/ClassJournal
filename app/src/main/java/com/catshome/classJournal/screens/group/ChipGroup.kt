package com.catshome.classJournal.screens.group

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.edgeSwipeToDismiss
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState
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
fun ChipGroup(
    viewModel: GroupViewModel,
    index: Int,
    //revealState: RevealState,
   // swipeToDismissBoxState: SwipeToDismissBoxState,
    content: @Composable (() -> Unit)
) {


    //TODO Тестирование удаления  и востановления функций
    val viewState by viewModel.viewState().collectAsState()
    // var revealState: RevealState
    //if (viewState.listItems[index].group.uid == viewState.swipeUid && viewState.revealState != null)
    if (viewState.listItems[index].revealState==null)
      viewState.listItems[index].revealState= rememberRevealState()
//    else
//        revealState = viewState.revealState?:rememberRevealState()
    //if (viewState.listItems[index].revealState != null)
    //   revealState= viewState.listItems[index].revealState

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
//    { it ->
//                Log.e("CLJR", "DISSMIS STATE")
//        viewState.revealState = revealState
//        true
//    }


    SwipeToRevealChip(
        revealState = viewState.listItems[index].revealState!!,
        modifier = Modifier.edgeSwipeToDismiss(swipeToDismissBoxState = swipeToDismissBoxState),
        primaryAction = {
            viewModel.obtainEvent(
                GroupEvent.SwipeUpdate(
                    viewState.listItems[index].group.uid, index = index,
                     revealState= viewState.listItems[index].revealState!!
                )
            )
            SwipeToRevealPrimaryAction(
                revealState = viewState.listItems[index].revealState!!,

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
                onClick = {
                    viewState.isDelete = true
                    CoroutineScope(Dispatchers.IO).launch {
                        viewState.listItems[index].revealState?.snapTo(RevealValue.RightRevealed)
//                        viewModel.obtainEvent(
//                            GroupEvent.SwipeUpdate(
//                                viewState.listItems[index].group.uid,
//                                revealState
//                            )
//                        )
                        delay(4000L)
                        if (viewState.isDelete) {
                            viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.listItems[index].group.uid))
                            viewState.listItems[index].revealState?.snapTo(RevealValue.Covered)
                            //viewModel.obtainEvent(GroupEvent.SwipeUpdate(-1, revealState))
                        }
                    }
                }
            )
        },
        undoPrimaryAction = {
            Log.e("ClJR", "UNDO")
            SwipeToRevealUndoAction(
                modifier = Modifier.background(ClassJournalTheme.colors.controlColor),
                revealState =  viewState.listItems[index].revealState!!,
                label = { Text("Undo") },
                onClick = {
                    viewModel.obtainEvent(
                        GroupEvent.UndoDeleteClicked(
                            viewState.listItems[index].group.uid
                        )
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        viewState.listItems[index].revealState?.snapTo(RevealValue.Covered)
                        //viewModel.obtainEvent(GroupEvent.SwipeUpdate(-1, revealState))
                    }
                }
            )
        },
        onFullSwipe = {
            viewState.isDelete = true
            CoroutineScope(Dispatchers.IO).launch {
                viewState.listItems[index].revealState?.snapTo(RevealValue.RightRevealed)
//                viewModel.obtainEvent(
//                    GroupEvent.SwipeUpdate(
//                        viewState.listItems[index].group.uid,
//                        revealState
//                    )
//                )
                delay(4000L)
                if (viewState.isDelete) {
                    viewModel.obtainEvent(GroupEvent.DeleteClicked(viewState.listItems[index].group.uid))
                    viewState.listItems[index].revealState?.snapTo(RevealValue.Covered)
                    //viewModel.obtainEvent(GroupEvent.SwipeUpdate(-1, revealState))
                }
            }
        },
        content = content
    )
}


