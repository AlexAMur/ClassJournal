package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.navigate.DetailsPay

data class NewVisitState(
    val searchText: String = "",
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    val isChildError: Boolean =false,
    val ChildErrorMessage: String? = null,
    var indexFocus:Int = -1,
    var isResetState: Boolean = false,
    var isSurnameError: Boolean= false,
    var isSnackbarShow: Boolean= false,
    var snackbarAction: String = "",
    var isPayError: Boolean= false,
    //var Error: String = "",
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val listPays: List<Visit>

)
