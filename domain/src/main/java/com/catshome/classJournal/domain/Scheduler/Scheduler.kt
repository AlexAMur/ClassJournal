 package com.catshome.classJournal.domain.Scheduler

data class Scheduler(
    val uid: String? = null,
    val dayOfWeek: String,
    val dayOfWeekInt: Int,
    val uidChild: String?  = null,
    val uidGroup: String?  = null,
    val name: String?  = null,
    val startLesson: Long?  = null,
    val duration: Int?  = null
)
