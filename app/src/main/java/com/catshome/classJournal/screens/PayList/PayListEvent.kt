package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Pay.Pay
import com.catshome.classJournal.navigate.DetailsPayResult
import com.catshome.classJournal.navigate.OptionFilterPaysList

sealed class PayListEvent {
    data class UpdateClicked(val pay: Pay): PayListEvent()
    data object NewClicked : PayListEvent()
    data object isFilterDataClicked : PayListEvent()
    data object isFilterChildClicked: PayListEvent()
    data object ReloadScreen : PayListEvent()
    data class ShowFAB(val isShowFAB: Boolean): PayListEvent()
    data class onCollapse(val isCollapse: Boolean): PayListEvent()
    data class ShowSnackBar(val detailsPayResult: DetailsPayResult): PayListEvent()
    data class Search(val  searchText: String): PayListEvent()
    class DeleteClicked(val uid: String,val index: Int) : PayListEvent()
    class UndoDeleteClicked(val uid: String, val index: Int) : PayListEvent()
    class SetOption(val option: OptionFilterPaysList) : PayListEvent()
}