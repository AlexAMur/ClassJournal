package com.catshome.classJournal.screens.PayList


import android.util.Log
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.PayListInteractor
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.navigate.DetailsPayResult
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.PayList.PayListAction.*
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Clock.System.now

@HiltViewModel
class PayListViewModel @Inject constructor(private val payListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, PayListAction, PayListEvent>(
        installState = PayListState(
            beginDate = "01.${
                if (LocalDateTime.now().month.value.toString().length == 1)
                    "0${LocalDateTime.now().month.value}"
                else
                    "${LocalDateTime.now().month.value}"
            }.${LocalDateTime.now().year} 00:00",
            endDate = "${now().toDateTimeRuString()?.substring(0, DATE_FORMAT_RU.length)} 23:59"
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
                viewState.deletePayUid = viewEvent.pay.uidPay
                viewState.isCanShowSnackBar = true
                viewState = viewState.copy(items = viewState.items.map {
                    if (viewEvent.pay.uidPay == it.uidPay)
                        it.copy(isDelete = true)
                    else
                        it
                })
                viewState.onAction = {
                    viewState.isCanShowSnackBar = false
                    viewState.isShowSnackBar = false
                    obtainEvent(
                        PayListEvent.UndoDeleteClicked(
                            viewState.deletePayUid
                        )
                    )
                    viewState.onAction =null
                }
                viewState.onDismissed = {
                    var result = false
                    obtainEvent(
                        PayListEvent.ShowSnackBar(
                            DetailsPayResult(
                                isShowSnackBar = false,
                                message = ""
                            ),
                        ))
                        val jobDef =  CoroutineScope(Dispatchers.Default).launch {
                            val job = CoroutineScope(Dispatchers.IO).async {
                                Log.e("CLJR", "Async!!!!")
                                return@async payListInteractor.deletePay(pay = viewEvent.pay.toPay())
                            }

                            if (!job.await()) {
                                Log.e("CLJR", "Error save!!!")

                                viewState.isCanShowSnackBar = true
                                obtainEvent(
                                    PayListEvent.ShowSnackBar(
                                        DetailsPayResult(
                                            message = context.getString(R.string.error_save),
                                            isShowSnackBar = true
                                            ),
                                        onDissmited = {
                                            viewState.isCanShowSnackBar = false
                                            viewState.isShowSnackBar = false
                                            job.cancel()

                                        }
                                    )
                                )
                            }
                        }
                    viewState.onAction =null
                    viewState.onDismissed = null
                }
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
                Log.e("CLJR", "UnDelete")
                viewState = viewState.copy(
                    items = viewState.items.map {
                        if (viewEvent.uidPay == it.uidPay) {
                            Log.e("CLJR", "isDelete is false")
                            it.copy(isDelete = false, isOptionsRevealed = false)
                        } else
                            it
                    })

            }

            is PayListEvent.ShowSnackBar -> {
                Log.e("CLJR", "Show SnackBar is ${viewEvent.detailsPayResult} ")
                Log.e("CLJR", "Show isShowSnackBar is ${viewState.isShowSnackBar} ")

                viewState = viewState.copy(
                    isShowSnackBar = viewEvent.detailsPayResult.isShowSnackBar,
                    messageShackBar = viewEvent.detailsPayResult.message
                )
                if (viewEvent.onAction == null)
                    viewState.onAction = {viewState.isShowSnackBar =false}
                if (viewEvent.onDissmited == null)
                    viewState.onDismissed = {viewState.isShowSnackBar =false}
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

            is PayListEvent.UpdateClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = EditPay(viewEvent.pay.toPay())
            }

            is PayListEvent.ChangeRevealed -> {
                viewState = viewState.copy(items = viewState.items.mapIndexed { index, pay ->
                    if (index == viewEvent.index)
                        pay.copy(isOptionsRevealed = viewEvent.isOptionsRevealed)
                    else
                        pay
                }
                )
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
                viewState = viewState.copy(items = listPay.map { it.toPayScreen() })
            }
        }
    }
}