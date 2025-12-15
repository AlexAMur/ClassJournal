package com.catshome.classJournal.screens.PayList

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.PayListInteractor
import com.catshome.classJournal.domain.communs.toDateRu
import com.catshome.classJournal.domain.communs.toLocalDateTime
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.navigate.DetailsPayList
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class PayListViewModel @Inject constructor(private val payListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, PayListAction, PayListEvent>(
        installState = PayListState()
    ) {
//    fun beginDateChange(newValue: String) {
//        viewState = viewState.copy(beginDate = newValue)
//    }
//    fun endDateChange(newValue: String) {
//        viewState = viewState.copy(endDate = newValue)
//    }
//    fun Search(newValue: String) {
//        viewState = viewState.copy(searchText = newValue)
//       // if (viewState.selectChild.name.isNotEmpty())
//            //viewState = viewState.copy(isNameError = false)
//    }

    override fun obtainEvent(viewEvent: PayListEvent) {
        when (viewEvent) {
            is PayListEvent.SetOption -> {

                with(viewEvent.option) {
                    viewState = viewState.copy(
                        isShowSnackBar = isShowSnackBar && viewState.isCanShowSnackBar,
                        messageShackBar = Message ?: "",
                        selectedOption = selectOption,
                        beginDate = beginDate ?: "",
                        endDate = endDate ?: "",
                        selectChild = MiniChild(uid = childId ?: "", fio = childFIO ?: "")
                    )
                }
                loadPayList()
            }

            is PayListEvent.DeleteClicked -> {
                payListInteractor.deletePay(viewState.items[viewState.index])
            }

            is PayListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = PayListAction.NewPay
            }

            is PayListEvent.ShowFAB -> {
                viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
            }

            is PayListEvent.ReloadScreen -> {
                loadPayList()
            }

            is PayListEvent.UndoDeleteClicked -> {
                //TODO emplemention undelete pay
            }

            is PayListEvent.ShowSnackBar -> {
                viewState = viewState.copy(
                    isShowSnackBar = viewEvent.isShow,
                    messageShackBar = viewEvent.message
                )
                if (viewEvent.isShow ==false)
                    viewState.isCanShowSnackBar =false
            }

            is PayListEvent.onCollapse -> {
                viewAction = PayListAction.OpenFilter
            }

            is PayListEvent.Search -> { //тут отбираем список детей
                viewState = viewState.copy(searchText = viewEvent.searchText)
                if (viewState.searchText.isEmpty()) {
                    viewState = viewState.copy(selectChild = null)
                    return
                }
                viewModelScope.launch {
                    payListInteractor.searchChild(viewEvent.searchText).collect {
                        if (it.isNullOrEmpty()) {
                            viewState =
                                viewState.copy(
                                    listSearch = listOf(
                                        MiniChild(
                                            uid = "",
                                            fio = "пусто"
                                        )
                                    )
                                )
                            return@collect
                        }
                        it.let {
                            viewState = viewState.copy(listSearch = it)
                        }
                    }
                }
            }

            is PayListEvent.isFilterChildClicked -> {
                viewState = viewState.copy(isFilterChild = !viewState.isFilterChild)
            }

            is PayListEvent.isFilterDataClicked -> {
                viewState = viewState.copy(isFilterData = !viewState.isFilterData)
            }
        }
    }

    fun loadPayList() {
        viewModelScope.launch {
            payListInteractor.getPays(
                viewState.selectChild?.uid,
                viewState.beginDate.toLocalDateTime()?.toLong(),
                viewState.endDate.toLocalDateTime()?.toLong(),
            )?.collect { listPay ->
                viewState = viewState.copy(items = listPay)
            }
        }
    }
}