package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.screens.PayList.NewPayEvent

sealed class NewVisitEvent {
    data object SaveClicked : NewVisitEvent()
    data object CancelClicked :NewVisitEvent()
    data class Search(val searchText: String): NewVisitEvent()
}