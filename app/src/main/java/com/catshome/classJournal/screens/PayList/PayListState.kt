package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.communs.toDateRuString
import java.time.LocalDateTime

data class PayListState(
    val showFAB: Boolean = false,
    val filterCollapse: Boolean = false,
    val isFilterData: Boolean =true,
    val isFilterChild: Boolean =false,
    val selectChild: MiniChild? = null,
    val listSearch: List<MiniChild>? = null,
    val searchText: String = "",
    val selectedOption: Int = 1,
    val beginDate: String = "",
    val endDate: String = "",
    val allRange: Boolean= true,
    val index: Int =-1,
    val items: List<Pay> = emptyList<Pay>(),
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val showFilter: Boolean = false,
    val messageShackBar:String? = null,
    val snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null,
    )
