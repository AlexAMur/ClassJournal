package com.catshome.classJournal.screens.Scheduler

import androidx.collection.emptyIntList
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.navigate.NewLesson

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
    var isNewLesson: Boolean = false,
    var oldTimeLesson: Int? = null,
    var timeLesson: Int?  = null,
    var durationLesson: Int? = null,
    var oldDurationLesson: Int? = null,
    val items: Map<String,List<Scheduler>?> = emptyMap(),
    val newScheduler: Scheduler? =null
)
