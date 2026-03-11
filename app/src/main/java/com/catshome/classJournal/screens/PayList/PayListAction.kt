package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Pay.Pay

sealed class PayListAction {
    data object NewPay: PayListAction()
    data class EditPay(val pay: Pay): PayListAction()
    data object OpenFilter: PayListAction()
    data object CloseScreen: PayListAction()
}