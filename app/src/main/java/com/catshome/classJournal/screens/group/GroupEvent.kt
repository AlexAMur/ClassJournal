package com.catshome.classJournal.screens.group

import androidx.room.Index
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        data object ReloadScreen : GroupEvent()
   //     data object CancelDelete : GroupEvent()


        class SwipeUpdate @OptIn(ExperimentalWearMaterialApi::class)
        constructor(val uid: Long, val index: Int,
                 val revealState: RevealState) : GroupEvent()
        class DeleteClicked(val uid: Long) : GroupEvent()
        class UndoDeleteClicked(val uid: Long) : GroupEvent()
        //data object ActionInvoked : NewGroupEvent()
}