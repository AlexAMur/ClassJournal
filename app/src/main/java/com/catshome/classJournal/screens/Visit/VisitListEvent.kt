package com.catshome.classJournal.screens.Visit

sealed class VisitListEvent {
    data object NewVisit: VisitListEvent()
    data object Reload: VisitListEvent()
    data class  ShowFAB(val isShowFAB: Boolean): VisitListEvent()
    data class EditVisit(val uidVisit: String): VisitListEvent()
    data class  DeleteVisit(val key: String, val uidVisit: String ): VisitListEvent()
    //data class  isDelete(val key: String, val index: Int): VisitListEvent()

}