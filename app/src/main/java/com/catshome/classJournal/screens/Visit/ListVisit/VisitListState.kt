package com.catshome.classJournal.screens.Visit.ListVisit

import com.catshome.classJournal.domain.Visit.Visit
import kotlinx.datetime.LocalDateTime


data class VisitListState(
    val index: Int =-1,
    var messageSnackBar: String? = null,
    var snackBarLabel: String? = null,
    val onAction: (()->Unit)? = {},
    var onDismissed: (()->Unit)? = {},
    val listVisit: Map<String,List<Visit?>?> = emptyMap(),
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isShowFAB: Boolean =  false,
    val deleteKey: String? = null,
    var deleteVisit: Visit? = null,
    var seleteVisit: Visit? = null
)
