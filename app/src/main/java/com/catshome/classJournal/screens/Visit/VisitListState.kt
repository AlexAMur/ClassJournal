package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Visit.Visit
import com.google.android.material.snackbar.Snackbar


data class VisitListState(
    val index: Int =-1,
    var messageSnackBar: String? = null,
//    var isCanDelete: Boolean = false,
    var snackBarLabel: String? = null,
    val onAction: (()->Unit)? = {},
    var onDismissed: (()->Unit)? = {},
    val listVisit: Map<String,List<Visit?>?> = emptyMap(),
    val isShowFAB: Boolean =  false,
    val deleteKey: String? = null,
    var deleteVisit: Visit? = null
)
