package com.catshome.classJournal.screens.Visit

sealed class VisitListAction {
        data object CloseScreen: VisitListAction()
        data object NewVisit: VisitListAction()
}