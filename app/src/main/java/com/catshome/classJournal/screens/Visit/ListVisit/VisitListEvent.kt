package com.catshome.classJournal.screens.Visit.ListVisit

import com.catshome.classJournal.navigate.OptionFilterList

sealed class VisitListEvent {
    data class onCollapse(val collapse: Boolean): VisitListEvent()
    data object NewVisit: VisitListEvent()
    data object Reload: VisitListEvent()
    data class  ShowFAB(val isShowFAB: Boolean): VisitListEvent()
    data class EditVisit(val uidVisit: String): VisitListEvent()
    data class  DeleteVisit(val key: String, val uidVisit: String ): VisitListEvent()
    data class  SetFilter(val filter: OptionFilterList): VisitListEvent()

}