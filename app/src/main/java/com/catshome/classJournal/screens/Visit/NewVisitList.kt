package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Visit.Visit

data class NewVisitList (
        val visit: Visit? =null,
        val isCheck: Boolean= false,
        val isError: Boolean= false
)