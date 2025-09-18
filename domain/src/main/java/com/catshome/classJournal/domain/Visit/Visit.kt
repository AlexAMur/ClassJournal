package com.catshome.classJournal.domain.Visit

import java.util.UUID

data class Visit(
    val uid: String = UUID.randomUUID().toString(),
    val fio: String = "",
    val data: String = "",
    val price: Int =0
)
