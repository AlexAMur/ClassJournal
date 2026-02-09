package com.catshome.classJournal.screens.Scheduler.newScheduler

import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlin.time.Duration

data class NewSchedulerState(
    var dayOfWeek: DayOfWeek?= null,
    var startTime: Int = 0,
    var duration: Int = 0,
    var searchText: String = "",
    val itemsList: List<ClientScheduler>? = null
)
