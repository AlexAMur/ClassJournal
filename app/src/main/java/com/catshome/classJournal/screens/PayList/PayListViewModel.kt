package com.catshome.classJournal.screens.PayList

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.PayListInteractor
import com.catshome.classJournal.domain.communs.toDateRuString
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Clock.System.now

@HiltViewModel
class PayListViewModel @Inject constructor(private val payListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, PayListAction, PayListEvent>(
        installState = PayListState(
            beginDate = "01.${if(LocalDateTime.now().month.value.toString().length==1)
                "0${LocalDateTime.now().month.value}"
            else
                "${LocalDateTime.now().month.value}"
            }.${LocalDateTime.now().year} 00:00",
            endDate = "${now().toLocalDateTime(TimeZone.currentSystemDefault()).toDateRuString()} 23:59"
        )
    ) {

    override fun obtainEvent(viewEvent: PayListEvent) {
        when (viewEvent) {

            is PayListEvent.SetOption -> {
                with(viewEvent.option) {
                    viewState = viewState.copy(
                        selectedOption = selectOption,
                        beginDate = beginDate.toString(),
                        endDate = endDate.toString(),
                        sortValue = sort,
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
                    isShowSnackBar = viewEvent.detailsPayResult.isShowSnackBar,
                    messageShackBar = viewEvent.detailsPayResult.Message
                )
                if (viewEvent.detailsPayResult.isShowSnackBar == false)
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

            is PayListEvent.UpdateClicked ->{
                viewState.isCanShowSnackBar= true
                viewAction = PayListAction.EditPay(viewEvent.pay)
            }
        }
    }

    fun loadPayList() {
        viewModelScope.launch {
            payListInteractor.getPays(
                viewState.selectChild?.uid,
                viewState.beginDate.toLocalDateTimeRu()?.toLong(),
                viewState.endDate.toLocalDateTimeRu()?.toLong(),
                viewState.sortValue
            )?.collect { listPay ->
                viewState = viewState.copy(items = listPay)
            }
        }
    }
}