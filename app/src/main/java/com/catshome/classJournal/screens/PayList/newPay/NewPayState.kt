package com.catshome.classJournal.screens.PayList.newPay

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.Pay

data class NewPayState(
    val searchText: TextFieldValue = TextFieldValue(""),
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    val isChildError: Boolean =false,
    val childErrorMessage: String? = null,
    var indexFocus:Int = -1,
    var isResetState: Boolean = false,
    var isSurnameError: Boolean= false,
    var isSnackBarShow: Boolean= false,
    var snackBarAction: String = "",
    var isPayError: Boolean= false,
    var payError: String = "",
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val payment: TextFieldValue = TextFieldValue("0", selection = TextRange(0, 1)),
    val pay: Pay = Pay()
)
