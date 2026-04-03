package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Visit.Visit

data class NewVisitState(
    val searchText: String = "",
    val isChildError: Boolean = false,
    val ChildErrorMessage: String = "",
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    var indexFocus:Int = -1,
    var isResetState: Boolean = false,
    var isSurnameError: Boolean= false,
    var isSnackbarShow: Boolean= false,
    var snackbarAction: String = "",
    var isPriceError: Boolean= false,
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val scheduler: List<Scheduler>? = null,
    val listVisit: List<Visit>

)
