package com.catshome.classJournal.screens.PayList


import com.catshome.classJournal.domain.PayList.Pay

data class PayListState(
    val index: Int =-1,
    val items: List<Pay> = emptyList<Pay>()
)
