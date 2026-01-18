package com.catshome.classJournal.Scheduler

data class SchedulerListState(
    val showFAB: Boolean = false,
    val messageShackBar: String = "",
    val snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val isShowSnackBar: Boolean = false,
    val isCanShowSnackBar: Boolean = false,
    val items: List<Scheduler> = emptyList()
)
