package com.catshome.classJournal.screens.Visit

sealed class VisitListEvent {
    data object NewVisit: VisitListEvent()

}