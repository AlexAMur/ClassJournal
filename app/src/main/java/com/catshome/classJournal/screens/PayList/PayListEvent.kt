package com.catshome.classJournal.screens.PayList

import android.os.Message

sealed class PayListEvent {
    data object NewClicked : PayListEvent()
    data object isFilterDataClicked : PayListEvent()
    data object isFilterChildClicked: PayListEvent()
    data object ReloadScreen : PayListEvent()
    data class ShowFAB(val isShowFAB: Boolean): PayListEvent()
    data class onCollapse(val isCollapse: Boolean): PayListEvent()
    data class ShowSnackBar(val isShow: Boolean,val  message: String): PayListEvent()
    data class Search(val  searchText: String): PayListEvent()
    class DeleteClicked(val uid: String,val index: Int) : PayListEvent()
    class UndoDeleteClicked(val uid: String, val index: Int) : PayListEvent()
}