package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.screens.group.GroupEvent

sealed class PayListEvent {
    data object NewClicked : PayListEvent()
    data object ReloadScreen : PayListEvent()
    class DeleteClicked(val uid: String,val index: Int) : PayListEvent()
    class UndoDeleteClicked(val uid: String, val index: Int) : PayListEvent()
}