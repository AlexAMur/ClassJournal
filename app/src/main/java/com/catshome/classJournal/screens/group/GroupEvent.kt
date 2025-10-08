package com.catshome.classJournal.screens.group


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        data object ReloadScreen : GroupEvent()
        class DeleteClicked(val uid: String,val index: Int) : GroupEvent()
        class UndoDeleteClicked(val uid: String, val index: Int) : GroupEvent()
}