package com.catshome.classJournal.screens.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler

data class SchedulerListState(
    var showStartTimePicker: Boolean = false,
    val messageShackBar: String = "",
    val snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val items: List<Scheduler> = emptyList()
)
