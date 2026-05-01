package com.catshome.classJournal.screens.Visit.ListVisit

import com.catshome.classJournal.domain.Visit.Visit


sealed class VisitListAction {
        //data object CloseScreen: VisitListAction()
        data object NewVisit: VisitListAction()
        data class EditVisit(val visit: Visit): VisitListAction()
}