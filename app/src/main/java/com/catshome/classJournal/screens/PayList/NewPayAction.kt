package com.catshome.classJournal.screens.PayList

sealed class NewPayAction {
    data object CloseScreen: NewPayAction()
    data object Successful: NewPayAction()
}
