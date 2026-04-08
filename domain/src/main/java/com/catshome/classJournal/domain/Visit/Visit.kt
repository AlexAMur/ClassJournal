package com.catshome.classJournal.domain.Visit

data class Visit(
    val uid: String? = "",
    val startLesson:Int = 0 ,
    val uidChild: String? = "",
    val fio: String = "",
    val groupName: String? =null,
    val data: String? = "",
    val price: Int? =0,
    val check: Boolean? = false
)

