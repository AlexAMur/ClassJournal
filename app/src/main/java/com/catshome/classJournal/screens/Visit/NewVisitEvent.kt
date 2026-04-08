package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.communs.DayOfWeek

sealed class NewVisitEvent {
    data object SaveClicked : NewVisitEvent()
    data object CancelClicked :NewVisitEvent()
    data object LessonClicked :NewVisitEvent()
    data class Search(val searchText: String): NewVisitEvent()
    data class ChangePageIndex(val index: Int): NewVisitEvent()
    data class getScheduler(val dayOfWeek: DayOfWeek): NewVisitEvent()
}