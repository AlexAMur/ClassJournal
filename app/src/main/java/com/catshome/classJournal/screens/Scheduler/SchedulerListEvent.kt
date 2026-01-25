package com.catshome.classJournal.screens.Scheduler

sealed class SchedulerListEvent {
    data object NewClicked: SchedulerListEvent()
    data object NewLesson: SchedulerListEvent()
    data class ShowSnackBar(val message: String): SchedulerListEvent()

    data class ShowTimePiker(val show: Boolean): SchedulerListEvent()
}