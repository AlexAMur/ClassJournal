package com.catshome.classJournal.screens.PayList.newPay

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.Pay
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock.System.now

data class NewPayState(
    val searchText: TextFieldValue = TextFieldValue(""),
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    val isChildError: Boolean =false,
    val childErrorMessage: String? = null,
    var indexFocus:Int = -1,
    var isPaySumFocus: Boolean= false,
    var isResetState: Boolean = false,
    var isSurnameError: Boolean= false,
    var isSnackBarShow: Boolean= false,
    var snackBarAction: String = "",
    var isPayError: Boolean= false,
    var payError: String = "",
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    var payment: TextFieldValue = TextFieldValue(""),
    val pay: Pay = Pay(datePay = now().toLocalDateTime(TimeZone.currentSystemDefault()).toDateTimeRuString().toString())
)
