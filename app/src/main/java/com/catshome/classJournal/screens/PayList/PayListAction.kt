package com.catshome.classJournal.screens.PayList

sealed class PayListAction {
    data object NewPay: PayListAction()
    data object OpenFilter: PayListAction()
    data object CloseScreen: PayListAction()
}