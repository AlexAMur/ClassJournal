package com.catshome.classJournal.screens.Scheduler.newScheduler

import com.catshome.classJournal.domain.Scheduler.ClientScheduler

data class NewSchedulerState(
    val startTime: Int = 0,
    val searchText: String = "",
    val itemsList: List<ClientScheduler>? = null
)
