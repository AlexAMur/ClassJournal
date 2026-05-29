package com.catshome.classJournal.screens.Visit

import android.net.TetheringManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import kotlinx.datetime.LocalDateTime
import java.util.Collections.emptyList
import kotlin.collections.mutableListOf

data class NewVisitState(
    var searchText: TextFieldValue = TextFieldValue(""),
    val isSelectChild: Boolean = false,
    val isSearchError: Boolean = false,
    val isShowDateDialog: Boolean = false,
    val isShowDialog: Boolean = false,
    val searchErrorMessage: String = "",
    val priceErrorMessage: String = "",
    val listChild: List<MiniChild>? = null,
    val selectChild: MiniChild? = null,
    val selectDate: Long? = null,
    var indexFocus: Int = -1,
    val pageIndex: Int = 0,
    var dateOnPage: LocalDateTime? = null,
    var pageDayOfWeekOffset: Int = Int.MAX_VALUE / 2,
    var lessonChecked: MutableList<MutableList<Boolean>> = mutableListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    ),
    var isSurnameError: Boolean = false,
    var isSnackBarShow: Boolean = false,
    var snackBarAction: String = "",
    var isPriceError: Boolean = false,
    var errorMessage: String = "",
    var errorMessageHaider: String = "",
    var onDismissed: (() -> Unit)? = null,
    var onDismissDialog: (() -> Unit)? = null,
    var onAction: (() -> Unit)? = null,
    val scheduler: List<Map<String, List<Visit>?>?> = emptyList(),
    val priceScreen: TextFieldValue = TextFieldValue(
                                                text = "0",
                                                selection = TextRange("0".length)

                        ),
    val visit: Visit? = null
)
