package com.catshome.classJournal.domain.Scheduler

import java.time.DayOfWeek

data class Scheduler(
    val uid: String? = null,
    val dayOfWeek: String,
    val dayOfWeekInt: Int,
    val uidChild: String?,
    val uidGroup: String?,
    val name: String,
    val startLesson: Long,
    val duration: Int
)
