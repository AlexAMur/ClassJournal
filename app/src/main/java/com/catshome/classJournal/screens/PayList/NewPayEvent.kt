package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.Pay

sealed class NewPayEvent {
    data object SaveClicked : NewPayEvent()
    data object CancelClicked : NewPayEvent()
    data class Search(val  searchText: String): NewPayEvent()
    data class SelectedChild(val  child: MiniChild): NewPayEvent()
    data class SetState(val  pay: Pay): NewPayEvent()

   data object ResetState: NewPayEvent()
}
