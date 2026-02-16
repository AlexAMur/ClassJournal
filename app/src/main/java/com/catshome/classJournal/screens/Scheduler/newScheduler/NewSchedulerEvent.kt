package com.catshome.classJournal.screens.Scheduler.newScheduler

sealed class NewSchedulerEvent {

    data object SaveEvent: NewSchedulerEvent()
    data object ClearState: NewSchedulerEvent()
    data object ReloadClient: NewSchedulerEvent()
    data object ClearSearch: NewSchedulerEvent()
    data object CloseEvent: NewSchedulerEvent()

    data class Search(val search: String): NewSchedulerEvent()
    data class Checked(val index: Int): NewSchedulerEvent()
}