package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.communs.toDateStringRU
import java.time.LocalDateTime
import java.util.Date

data class PayListState(
    val showFAB: Boolean = false,
    val filterCollapse: Boolean = false,
    val isFilterData: Boolean =true,
    val isFilterChild: Boolean =false,
    val selectChild: MiniChild? = null,
    val listSearch: List<MiniChild>? = null,
    val searchText: String = "",
    val selectedOption: Int = 0,
    val beginDate: String = "01.${LocalDateTime.now().month.value}.${LocalDateTime.now().year}",
    val endDate: String = Date().time.toDateStringRU(),
    val allRange: Boolean= true,
    val index: Int =-1,
    val items: List<Pay> = emptyList<Pay>(),
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val showFilter: Boolean = false,
    val messageShackBar:String = "",
    val snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    )
