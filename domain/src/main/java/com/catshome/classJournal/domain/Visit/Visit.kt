package com.catshome.classJournal.domain.Visit

import com.catshome.classJournal.domain.communs.DayOfWeek

data class Visit(
    val uid: String? = "",
    val dayOfWeek: Int? = null,
    val startLesson:Int = 0 ,
    val uidChild: String? = "",
    val fio: String = "",
    val groupName: String? =null,
    val data: String? = "",
    val price: Int? =0,
    val check: Boolean? = false
)

