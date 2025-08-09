package com.catshome.classJournal.screens.group

import androidx.room.Index
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        data object ReloadScreen : GroupEvent()
        class DeleteClicked(val uid: Long,val index: Int) : GroupEvent()
        class UndoDeleteClicked(val uid: Long, val index: Int) : GroupEvent()
}