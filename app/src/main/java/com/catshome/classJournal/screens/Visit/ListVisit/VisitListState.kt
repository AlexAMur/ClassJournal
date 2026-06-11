package com.catshome.classJournal.screens.Visit.ListVisit

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.datetime.LocalDateTime


data class VisitListState(
    val index: Int =-1,
    var messageSnackBar: String? = null,
    var snackBarLabel: String? = null,
    val onAction: (()->Unit)? = {},
    var onDismissed: (()->Unit)? = {},
    val listVisit: Map<String,List<Visit?>?> = emptyMap(),

    val isShowFAB: Boolean =  false,
    val deleteKey: String? = null,
    var deleteVisit: Visit? = null,
    var seleteVisit: Visit? = null,

    val selectChild: MiniChild? = null,
    val selectedOption:Int = 1,
    val sortValue: SortEnum =  SortEnum.Date,
    var beginDate: LocalDateTime? = null,
    var endDate: LocalDateTime?= null,
)
