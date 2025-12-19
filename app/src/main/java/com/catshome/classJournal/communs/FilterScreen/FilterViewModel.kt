package com.catshome.classJournal.communs.FilterScreen

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.PayListInteractor
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val payListInteractor: PayListInteractor
) :
    BaseViewModel<FilterState, FilterAction, FilterEvent>(
        installState = FilterState()
    ) {
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
                    status = status + ", "
                if (viewState.beginDate.isNotEmpty() && viewState.endDate.isNotEmpty()) {
                    status =
                        status + "${viewState.optionList[viewState.selectedOption]} c ${viewState.beginDate} по ${viewState.endDate}"
                } else {
                    status = status + " ${viewState.optionList[viewState.selectedOption]}"
                }

                viewState = viewState.copy(
                    statusText = status
                )
            }

            is FilterEvent.ChildSelected -> {
                viewState = viewState.copy(
                    selectChild = viewEvent.child,
                    searchList = null,
                    searchText = viewEvent.child.fio,
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
                if (viewEvent.value.isNotEmpty()) {
                    viewModelScope.launch {
                        payListInteractor.searchChild(viewEvent.value).collect {
                            if (it.isNullOrEmpty()) {
                                viewState =
                                    viewState.copy(
                                        searchText = viewEvent.value,
                                        searchList = listOf(
                                            MiniChild(
                                                uid = "",
                                                fio = "пусто"
                                            )
                                        ),
                                        isShowList = true
                                    )
                                return@collect
                            }
                            it.let {
                                viewState = viewState.copy(
                                    searchText = viewEvent.value,
                                    searchList = it,
                                    isShowList = true
                                )
                            }
                        }
                    }
                } else {
                    viewState = viewState.copy(
                        searchText = viewEvent.value,
                        searchList = null,
                        isShowList = false
                    )
                }
            }

            FilterEvent.ClearSearch -> {
                viewState = viewState.copy(
                    searchText = "",
                    searchList = null,
                    isShowList = false,
                    selectChild = null
                )
                obtainEvent(FilterEvent.NewStatus)
            }

            FilterEvent.BackClick -> {
                viewAction = FilterAction.CloseScreen
            }

            is FilterEvent.Init -> {
                with(viewEvent.init) {
                    viewState = viewState.copy(
                        selectChild = MiniChild(
                            uid = childId ?: "",
                            fio = childFIO ?: ""
                        ),
                        selectedOption = optionsIndex,
                        beginDate = beginDate.toString(),
                        endDate = endDate.toString(),
                    )
                }
                obtainEvent(FilterEvent.NewStatus)
            }
        }
    }

    private fun setDateFilter() {
        when (viewState.selectedOption) {
            0 -> {
                viewState = viewState.copy(
                    beginDate = LocalDate.now().atStartOfDay().toDateTimeRuString(),
                    endDate = LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1)
                        .toDateTimeRuString()
                )
            }

            1 -> {
                viewState = viewState.copy(
                    beginDate = "01.${LocalDate.now().month.value}.${LocalDate.now().year} 00:00:00",
                    endDate = LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1)
                        .toDateTimeRuString()
                )
            }

            2 -> {
                viewState = viewState.copy(
                    beginDate = "01.01.${LocalDate.now().year} 00:00:00",
                    endDate = LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1)
                        .toDateTimeRuString()
                )
            }

            3 -> {//тут выбор переода стоит
                 }

            4 -> {
                viewState = viewState.copy(beginDate = "", endDate = "")
            }

            else -> {}
        }
    }
}
