package com.catshome.classJournal.communs.FilterScreen

sealed class FilterAction {
    data object Successful: FilterAction()
    data object CloseScreen: FilterAction()
}