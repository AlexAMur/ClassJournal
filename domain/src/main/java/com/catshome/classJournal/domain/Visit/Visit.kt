package com.catshome.classJournal.domain.Visit

import java.util.UUID

data class Visit(
    val uid: String = "",
    val uidChild: String = "",
    val fio: String = "",
    val data: String = "",
    val price: Int =0
)
