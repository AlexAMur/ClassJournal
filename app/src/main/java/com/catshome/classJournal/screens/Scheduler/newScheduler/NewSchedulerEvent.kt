package com.catshome.classJournal.screens.Scheduler.newScheduler

sealed class NewSchedulerEvent {
    data object SaveEvent: NewSchedulerEvent()
    data object CloseEvent: NewSchedulerEvent()
}