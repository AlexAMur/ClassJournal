package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.navigate.DetailsPayResult
import com.catshome.classJournal.navigate.OptionFilterList

sealed class PayListEvent {
    data object  resetSnackBar: PayListEvent()
    data class UpdateClicked(val pay: PayScreen): PayListEvent()
    data object NewClicked : PayListEvent()
    data object isFilterDataClicked : PayListEvent()
    data object isFilterChildClicked: PayListEvent()
    data object ReloadScreen : PayListEvent()
    data class ShowFAB(val isShowFAB: Boolean): PayListEvent()
    data class onCollapse(val isCollapse: Boolean): PayListEvent()
    data class ShowSnackBar(val detailsPayResult: DetailsPayResult,

                            val onAction: (()->Unit)? = null,
                            val onDissmited: (()->Unit)? = null
    ): PayListEvent()
    data class ChangeRevealed(val index: Int, val isOptionsRevealed: Boolean): PayListEvent()
    data class Search(val  searchText: String): PayListEvent()
    data class DeleteClicked(val pay: PayScreen) : PayListEvent()
    class UndoDeleteClicked(val pay: PayScreen?) : PayListEvent()
    class SetOption(val option: OptionFilterList) : PayListEvent()
}