package com.catshome.classJournal.screens.Scheduler.newScheduler

sealed class NewSchedulerEvent {
    data object SaveEvent: NewSchedulerEvent()
    data object ClearSearch: NewSchedulerEvent()
    data object CloseEvent: NewSchedulerEvent()
    data class Search(val search: String): NewSchedulerEvent()
}