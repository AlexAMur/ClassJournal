package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import java.util.Collections.emptyList

data class NewVisitState(
    val searchText: String = "",
    val isSearchError: Boolean = false,
    val isShowDateDialog: Boolean = false,
    val searchErrorMessage: String = "",
    val priceErrorMessage: String = "",
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    val selectDate: Long? = null,
    var indexFocus:Int = -1,
    val pageIndex: Int = 0,
    var lessonChecked: MutableList<Boolean> = emptyList(),
    var isSurnameError: Boolean= false,
    var isSnackBarShow: Boolean= false,
    var snackBarAction: String = "",
    var isPriceError: Boolean= false,
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val scheduler: List<Map<String,List<Visit>>?> = emptyList(),
    val priceScreen: String = "0",
    val visit: Visit? = null

)
//data class visitScjedulerPage(
//
//    val scheduler: Map<String,List<Visit>>? = null,
//)