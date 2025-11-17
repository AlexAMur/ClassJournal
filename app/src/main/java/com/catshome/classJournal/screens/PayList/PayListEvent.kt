package com.catshome.classJournal.screens.PayList

sealed class PayListEvent {
    data object NewClicked : PayListEvent()
    data object ReloadScreen : PayListEvent()
    data class ShowFAB(val isShowFAB: Boolean): PayListEvent()
    class DeleteClicked(val uid: String,val index: Int) : PayListEvent()
    class UndoDeleteClicked(val uid: String, val index: Int) : PayListEvent()
}