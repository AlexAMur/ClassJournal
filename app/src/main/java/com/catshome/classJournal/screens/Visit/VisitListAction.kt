package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.screens.PayList.PayListAction

sealed class VisitListAction {
    data object NewVisit: VisitListAction()
}