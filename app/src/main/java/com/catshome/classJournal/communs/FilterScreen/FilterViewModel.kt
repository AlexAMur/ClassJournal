package com.catshome.classJournal.communs.FilterScreen

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.PayListInteractor
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toStringRu
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val payListInteractor: PayListInteractor
) :
    BaseViewModel<FilterState, FilterAction, FilterEvent>(FilterState()) {
    fun beginDateChange(newValue: String) {
        viewState = viewState.copy(beginDate = newValue)
    }

    fun endDateChange(newValue: String) {
        viewState = viewState.copy(endDate = newValue)
    }

    override fun obtainEvent(viewEvent: FilterEvent) {
        when (viewEvent) {
            FilterEvent.NewStatus -> {
                var status = viewState.selectChild?.fio ?: ""
                if (status.isNotEmpty())
                    status = "$status , "
                if (!viewState.beginDate.isNullOrEmpty()) {// && !viewState.endDate.isNullOrEmpty()) {

                    status =
                        "$status ${viewState.optionList[viewState.selectedOption]} c ${viewState.beginDate} по ${viewState.endDate}"

                } else {
                    status = "$status ${viewState.optionList[viewState.selectedOption]}"
                }
                viewState = viewState.copy(
                    statusText = status
                )
            }

            is FilterEvent.ChildSelected -> {
                viewState = viewState.copy(
                    selectChild = viewEvent.child,
                    searchList = null,
                    searchText = TextFieldValue(
                        text = viewEvent.child.fio,
                        selection = TextRange(0, viewEvent.child.fio.length)
                    ),
                    isShowList = false
                )
                obtainEvent(FilterEvent.NewStatus)
            }

            FilterEvent.Successful -> {
                viewAction = FilterAction.Successful
            }

            is FilterEvent.SelectedIndex -> {
                viewState = viewState.copy(
                    selectedOption = viewEvent.index,
                    isShowPeriod = viewState.optionList[viewEvent.index] == context.getString(R.string.filter_period),
                )
                setDateFilter()
                obtainEvent(FilterEvent.NewStatus)
            }

            is FilterEvent.Search -> {
                viewState = viewState.copy(
                    searchText = viewEvent.value,
                )
                if (viewEvent.value.text.isNotEmpty()) {
                    viewModelScope.launch {
                        payListInteractor.searchChild(viewEvent.value.text).collect {
                            if (it.isNullOrEmpty()) {
                                viewState =
                                    viewState.copy(
                                        searchList = listOf(
                                            MiniChild(
                                                uid = "",
                                                fio = "пусто"
                                            )
                                        ),
                                        isShowList = true
                                    )
                                return@collect
                            } else {
                                viewState = viewState.copy(
                                    searchList = it,
                                    isShowList = viewState.selectChild == null
                                )

                            }
                        }
                    }
                } else {
                    viewState = viewState.copy(
                        searchList = null,
                        selectChild = null,
                        isShowList = false
                    )
                }
            }

            FilterEvent.ClearSearch -> {
                viewState.searchText = TextFieldValue("")
                viewState = viewState.copy(
                    searchList = null,
                    isShowList = false,
                    selectChild = null
                )
            }

            FilterEvent.BackClick -> {
                viewAction = FilterAction.CloseScreen
            }

            is FilterEvent.Init -> {
                with(viewEvent.init) {
//                    childId?.let {id->
//                        childFIO?.let {fio->
//                        viewState = viewState.copy(selectChild = MiniChild(uid= id, fio = fio))
//                        }
//                    }
//


                    viewState = viewState.copy(
                        selectChild = if (childId != null) {
                            MiniChild(uid = childId, fio = childFIO ?: "")
                        } else
                            null,
                        searchText = TextFieldValue(
                            childFIO ?: "",
                            selection = TextRange(0, childFIO?.length ?: 0)
                        ),
                        selectedOption = optionsIndex,
                        isShowList = false,
                        sortValue = sortEnum,
                        textSorting = viewState.sortList[sortEnum?.ordinal ?: 0],
                        beginDate = beginDate ?: "",
                        endDate = endDate ?: "",
                        screen = screen
                    )
                }
                obtainEvent(FilterEvent.NewStatus)
            }

            is FilterEvent.SelectSort -> {
                viewState = viewState.copy(textSorting = viewEvent.value)
                when (viewEvent.value) {
                    viewState.sortList[0] -> {
                        viewState.sortValue = SortEnum.Date
                    }

                    viewState.sortList[1] -> {
                        viewState.sortValue = SortEnum.FIO
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setDateFilter() {
        when (viewState.selectedOption) {
            0 -> { //filter one day
                viewState = viewState.copy(
                    beginDate = "${
                        Clock.System.now().toLocalDateTime(
                            timeZone = TimeZone.currentSystemDefault()
                        )
                            .toDateTimeRuString(formatDate = FormatDate.Date).toString()
                    } 00:00",
                    endDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        .toDateTimeRuString().toString()
                )
            }

            1 -> {  // 30 day
                viewState = viewState.copy(
                    beginDate = "${
                        Clock.System.now().toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        ).date.minus(
                            value = 1,
                            DateTimeUnit.MONTH
                        ).toStringRu()
                    } 00:00",
                    endDate = "${
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            .toDateTimeRuString(formatDate = FormatDate.Date)
                    } 23:59"
                )
            }

            2 -> { // filter s 01.01 to current day
                viewState = viewState.copy(
                    beginDate = "01.01.${
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
                    } 00:00",
                    endDate = "${
                        Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date.atStartOfDayIn(
                            TimeZone.currentSystemDefault()
                        ).toDateTimeRuString(formatDate = FormatDate.Date)
                    } 23:59"
                )
            }

            3 -> {//тут выбор периода стоит
//                if(viewState.beginDate.isNullOrEmpty() || viewState.endDate.isNullOrEmpty())

                viewState = viewState.copy(
                    beginDate = "01.${
                        if (Clock.System.now()
                                .toLocalDateTime(TimeZone.currentSystemDefault()).month.ordinal < 9
                        )
                            "0${
                                Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).month.ordinal + 1
                            }"
                        else
                            "${
                                Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).month.ordinal + 1
                            }"
                    }.${
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
                    } 00:00",
                    endDate = "${
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            .toDateTimeRuString(formatDate = FormatDate.Date)
                    } 23:59"
                )
            }

            4 -> {
                viewState.beginDate = null
                viewState.endDate = null
            }

            else -> {}
        }
    }
}
