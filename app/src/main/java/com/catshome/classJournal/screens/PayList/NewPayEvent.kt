package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.MiniChild

sealed class NewPayEvent {
    data object SaveClicked : NewPayEvent()
    data object CancelClicked : NewPayEvent()
    data class Search(val  searchText: String): NewPayEvent()
    data class SelectedChild(val  child: MiniChild): NewPayEvent()
   data object ResetState: NewPayEvent()
}
