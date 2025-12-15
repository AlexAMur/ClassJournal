package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay

data class NewPayState(
    val searchText: String = "",
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    val isChildError: Boolean =false,
    val ChildErrorMessage: String? = null,
    var indexFocus:Int = -1,
    var isSurnameError: Boolean= false,
    var isSnackbarShow: Boolean= false,
    var snackbarAction: String = "",
    var isPayError: Boolean= false,
    var PayError: String = "",
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val pay: Pay
)
