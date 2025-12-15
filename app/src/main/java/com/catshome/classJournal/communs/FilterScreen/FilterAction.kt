package com.catshome.classJournal.communs.FilterScreen

import com.catshome.classJournal.screens.PayList.PayListAction

sealed class FilterAction {
    data object Successful: FilterAction()
    data object CloseScreen: FilterAction()
}