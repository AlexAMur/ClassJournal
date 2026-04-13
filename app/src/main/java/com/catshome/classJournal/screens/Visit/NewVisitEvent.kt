package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.DayOfWeek

sealed class NewVisitEvent {
    data object SaveClicked : NewVisitEvent()
    data object CancelClicked :NewVisitEvent()
    data object ClearSelect :NewVisitEvent()
    data object LessonClicked :NewVisitEvent()
    data class Search(val searchText: String): NewVisitEvent()
    data class ChangePrice(val price: String): NewVisitEvent()
    data class SelectDate(val date: Long?): NewVisitEvent()
    data class ShowDateDialog(val isShow: Boolean): NewVisitEvent()
    data class SelectChild(val selectChild: MiniChild): NewVisitEvent()
    data class ChangePageIndex(val index: Int): NewVisitEvent()
   // data class getScheduler(val dayOfWeek: DayOfWeek): NewVisitEvent()
    data object getScheduler: NewVisitEvent()
}