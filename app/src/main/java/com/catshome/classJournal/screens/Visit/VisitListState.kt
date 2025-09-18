package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Visit.Visit


data class VisitListState(
    val index: Int =-1,
    val itemVisit: List<Visit> =emptyList()
)
