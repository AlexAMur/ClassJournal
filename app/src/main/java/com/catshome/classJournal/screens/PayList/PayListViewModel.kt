package com.catshome.classJournal.screens.PayList


import android.util.Log
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
import com.catshome.classJournal.screens.PayList.PayListAction.EditPay
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                var indexDelete =-1
                viewState = viewState.copy(items = viewState.items.mapIndexed { index, pay ->
                    if (viewEvent.pay.uidPay ==  pay.uidPay) {
                        indexDelete = index
                        pay.copy(isDelete = true)
                    }
                    else
                        pay
                })
                viewState.indexDelete = indexDelete
                viewState.onAction = {
                    //viewState.isCanShowSnackBar = false

                    obtainEvent(
                        PayListEvent.UndoDeleteClicked(
                            viewState.deletePayUid
                        )
                    )
//                    resetStatusSnackBar()
                    viewState.onDismissed = null
                    viewState.onAction = null
                    viewState.isCanShowSnackBar = false
                    viewState.messageShackBar = null
                }
                viewState.onDismissed = {
                    var result = false
                    Log.e("CLJR", "Before corutine")
                    if (viewState.items[viewState.indexDelete].isDelete) {
                        val jobDef = CoroutineScope(Dispatchers.Default).launch {
                            val job = CoroutineScope(Dispatchers.IO).async {
                                result = payListInteractor.deletePay(pay = viewEvent.pay.toPay())
                                Log.e("CLJR", "Async!!!! $result")
                                Log.e("CLJR", "Async viewEvent ${viewEvent.pay}")
                                return@async result
                            }
                            if (!job.await()) {
                                viewState.isCanShowSnackBar = true
                                viewState.messageShackBar = context.getString(R.string.error_save)
                                viewState.isCanShowSnackBar = false
                                viewState.withDismissAction =false

                                viewState.messageShackBar = null
                                viewState.onDismissed = null
                                viewState.onAction = null
                                //resetStatusSnackBar()
                            }
                        }
                    }
//                        viewState.onDismissed = null
//                        viewState.onAction = null

//                        viewState.isCanShowSnackBar = false
//                        viewState.messageShackBar = null
                        // resetStatusSnackBar()

                }
                viewState.isCanShowSnackBar = true
                viewState = viewState.copy(messageShackBar = "Отменить удаление ${viewEvent.pay.fio}?")
                Log.e("CLJR", "ViewModel DeleteClicked ${viewState.messageShackBar}")
            }

            PayListEvent.resetSnackBar -> {
                resetStatusSnackBar()
            }

            is PayListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewState.messageShackBar = null
                viewAction = PayListAction.NewPay
            }

            is PayListEvent.ShowFAB -> {
                viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
            }

            is PayListEvent.ReloadScreen -> {
                loadPayList()
            }

            is PayListEvent.UndoDeleteClicked -> {
                viewState = viewState.copy(
                    items = viewState.items.map {
                        if (viewEvent.uidPay == it.uidPay) {
                            it.copy(isDelete = false, isOptionsRevealed = false)
                        } else
                            it
                    })
                Log.e("CLJR", "UnDelete")
            }

            is PayListEvent.ShowSnackBar -> {
                Log.e("CLJR", "ShowSnackBar in viewModel ${viewEvent.detailsPayResult} ")
                //viewState.isCanShowSnackBar = true
                viewState = viewState.copy(
                    messageShackBar = viewEvent.detailsPayResult.message
                )
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
                viewState.messageShackBar = null
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

    fun setStatusSnackBar(
        isCanShowSnackBar: Boolean?,
        withDismissAction: Boolean = true,
        actionLabel:String? = null,
        messageSnackBar: String?,
        onAction: (() -> Unit)?,
        onDismissed: (() -> Unit)?,
    ) {
        isCanShowSnackBar?.let { isCan -> viewState = viewState.copy(isCanShowSnackBar = isCan) }
        viewState = viewState.copy(
            withDismissAction = withDismissAction,
            messageShackBar = messageSnackBar,
            snackBarAction = actionLabel
        )
        onAction?.let { viewState.onAction = it }
        onDismissed?.let { viewState.onDismissed = it }
    }

    private fun resetStatusSnackBar() {
        viewState.onAction = null
        viewState.onDismissed = null
     //   viewState = viewState.copy(isCanShowSnackBar = false, messageShackBar = null)
        viewState.isCanShowSnackBar = false
        //viewState.withDismissAction = true
        viewState.messageShackBar = null
    }
}