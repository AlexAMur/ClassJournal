package com.catshome.classJournal.screens.Visit

import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.navigate.VisitDetails

sealed class NewVisitEvent {
    data class EditVisit(val details: VisitDetails) : NewVisitEvent()
    data class SaveClicked(val openPage: Int,val dayOfWeek: DayOfWeek?) : NewVisitEvent()
    data object CancelClicked : NewVisitEvent()
    data class ItemCheckClicked(
        val dayInt: Int,
        val key: String,
        val indexItem: Int,
        val isCheck: Boolean
    ) : NewVisitEvent()

    data object ClearSelect : NewVisitEvent()
    data class LessonClicked(
        val dayInt: Int,
        val key: String,
        val isCheck: Boolean
    ) : NewVisitEvent()
    data class Search(val searchText: TextFieldValue) : NewVisitEvent()
    data class ChangePrice(val price: String) : NewVisitEvent()
    data class ChangePriceOnScheduler(
        val dayInt: Int,
        val key: String,
        val index: Int,
        val text: String,
    ) : NewVisitEvent()

    data class SelectDate(val date: Long?) : NewVisitEvent()
    data class ShowDateDialog(val isShow: Boolean) : NewVisitEvent()
    data class SelectChild(val selectChild: MiniChild) : NewVisitEvent()
    data class ChangePageIndex(val index: Int) : NewVisitEvent()

    // data class getScheduler(val dayOfWeek: DayOfWeek): NewVisitEvent()
    data object getScheduler : NewVisitEvent()
}