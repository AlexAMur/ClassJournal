package com.catshome.classJournal.screens.Visit

import android.R
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import java.util.Collections.emptyList

data class NewVisitState(
    val searchText: String = "",
    val isChildError: Boolean = false,
    val ChildErrorMessage: String = "",
    val listChild: List<MiniChild>? =null,
    val selectChild: MiniChild? = null,
    var indexFocus:Int = -1,
    val pageIndex: Int = 0,
    //var isResetState: Boolean = false,
    var lessonChecked: MutableList<Boolean> = emptyList(),
    var isSurnameError: Boolean= false,
    var isSnackbarShow: Boolean= false,
    var snackbarAction: String = "",
    var isPriceError: Boolean= false,
    var errorMessage: String = "",
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    val scheduler: Map<String,List<Visit>>? = null,
//    val listVisit: List<Visit>

)
