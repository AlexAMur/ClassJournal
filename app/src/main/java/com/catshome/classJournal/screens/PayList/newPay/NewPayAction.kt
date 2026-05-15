package com.catshome.classJournal.screens.PayList.newPay

sealed class NewPayAction {
    data object CloseScreen: NewPayAction()
    data object Successful: NewPayAction()
}