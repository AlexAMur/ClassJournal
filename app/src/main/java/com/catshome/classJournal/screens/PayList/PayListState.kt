package com.catshome.classJournal.screens.PayList


import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayItem

data class PayListState(
    val showFAB: Boolean = false,
    val index: Int =-1,
    val items: List<PayItem> = emptyList<PayItem>()
)
