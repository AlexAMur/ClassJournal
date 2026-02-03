package com.catshome.classJournal.screens.Scheduler

import androidx.collection.emptyIntList
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek

data class SchedulerListState(
    var showStartTimePicker: Boolean = false,
    val messageShackBar: String = "",
    val snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val dayList: List<Boolean> = listOf(false,false,false,false,false,false,false),
    var selectDay: DayOfWeek? = null,
    var selectTime: Int?  = null,
    var selectDuration: Int? = null,
    val items: Map<String,List<Scheduler>?> = emptyMap(),
    val newScheduler: Scheduler? =null
)
