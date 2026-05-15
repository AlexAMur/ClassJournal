package com.catshome.classJournal.screens.Scheduler.newScheduler

import androidx.compose.ui.text.input.TextFieldValue

sealed class NewSchedulerEvent {

    data object SaveEvent: NewSchedulerEvent()
    data object ClearState: NewSchedulerEvent()
    data object ReloadClient: NewSchedulerEvent()
    data object ClearSearch: NewSchedulerEvent()
    data object CloseEvent: NewSchedulerEvent()

    data class Search(val search: TextFieldValue): NewSchedulerEvent()
    data class ChangePrice(val index: Int,val price: String): NewSchedulerEvent()
    data class Checked(val index: Int): NewSchedulerEvent()
}