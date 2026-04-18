package com.catshome.classJournal.screens.Visit

sealed class NewVisitAction {
    data object CloseScreen: NewVisitAction()
    data object SaveAndCloseScreen: NewVisitAction()
}