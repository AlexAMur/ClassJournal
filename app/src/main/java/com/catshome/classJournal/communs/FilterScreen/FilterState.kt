package com.catshome.classJournal.communs.FilterScreen

import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild

data class FilterState(
    val optionList: List<String> = listOf(
        context.getString(R.string.filter_day),
                        context.getString(R.string.filter_month),
                        context.getString(R.string.filter_year),
                        context.getString(R.string.filter_period),
                        context.getString(R.string.filter_all),
                        ),
    val searchList: List<MiniChild>? = null,
    val textSorting: String =  "",
    val indexSorting: Int = 0,
    val selectedOption: Int = 0,
    val selectChild: MiniChild? = null,
    val isShowList: Boolean = false,
    val isShowPeriod: Boolean = false,
    val isFilterChild : Boolean =false,
    val isFilterData : Boolean =false,
    val searchText: String = "",
    val statusText: String = "",
    val beginDate: String = "",
    val endDate: String = "",
)