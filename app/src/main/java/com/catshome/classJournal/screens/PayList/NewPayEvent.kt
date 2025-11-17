package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.screens.child.NewChildEvent

sealed class NewPayEvent {
    data object SaveClicked : NewPayEvent()
    data object CancelClicked : NewPayEvent()
    data class Search(val  searchText: String): NewPayEvent()
    data class SelectedChild(val  child: Child): NewPayEvent()
}
