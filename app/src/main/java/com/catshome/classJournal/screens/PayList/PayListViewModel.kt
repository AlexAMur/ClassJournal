package com.catshome.classJournal.screens.PayList


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.PayList.mapToPayEntity
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.PayListInteractor
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.formatRu
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.PayList.PayListAction.EditPay
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Clock
import androidx.compose.runtime.getValue

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
            endDate = "${
                Clock.System.now().toDateTimeRuString()?.substring(0, DATE_FORMAT_RU.length)
            } 23:59"
        )
    ) {
    // Состояние загрузки для индикатора свайпа
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow().value

    init {
        getStatisticPay()
    }

    private val exceptionHandlerPaysList =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("CLJR","exceptionHandlerPaysList!! " )
            if (throwable.message?.contains("ErrorDelete") == true)

                obtainEvent(
                    PayListEvent.UndoDeleteClicked(
                        viewState.deletePays
                    )
                )
            viewState = viewState.copy(
                isCanShowSnackBar = true,
                messageSnackBar = if (throwable.message?.contains("ErrorDelete") == true)
                    "Не удалось удалить запись!"
                else
                    throwable.message.toString(),
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isCanShowSnackBar = false) },
                onAction = { viewState = viewState.copy(isCanShowSnackBar = false) }
            )
            return@CoroutineExceptionHandler

        }

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
                viewState.deletePays?.let { deletePay ->
                    deletePayDB(deletePay)
                }
                viewState.deletePays = viewEvent.pay

                viewState = viewState.copy(items = viewState.items.map { pay ->
                    if (viewEvent.pay.uidPay == pay.uidPay) {
                        pay.copy(isDelete = true)
                    } else
                        pay
                })
                viewState.onAction = {
                    obtainEvent(
                        PayListEvent.UndoDeleteClicked(
                            pay = viewState.deletePays
                        )
                    )
                    viewState.onDismissed = null
                    viewState.onAction = null
                    viewState.isCanShowSnackBar = false
                    viewState.messageSnackBar = null
                }
                viewState.onDismissed = {
                    var result = false
                    viewState.deletePays?.let {deletePays->
                        CoroutineScope(Dispatchers.Default).launch(exceptionHandlerPaysList) {
                            val job = CoroutineScope(Dispatchers.IO).async {
                                result =
                                    payListInteractor.deletePay(pay = deletePays.toPay())
                                return@async result
                            }
                            if (!job.await()) {
                                viewState.isCanShowSnackBar = true
                                viewState.messageSnackBar =
                                    context.getString(R.string.error_save)
                                viewState.isCanShowSnackBar = false
                                viewState.withDismissAction = false
                                viewState.messageSnackBar = null
                                viewState.onDismissed = null
                                viewState.onAction = null
                               viewState.deletePays = null
                            }
                        }
                    }
                }
                viewState.isCanShowSnackBar = true
                viewState =
                    viewState.copy(messageSnackBar = "Отменить удаление ${viewEvent.pay.fio}?")
            }

            PayListEvent.resetSnackBar -> {
                resetStatusSnackBar()
            }

            is PayListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewState.messageSnackBar = null
                viewAction = PayListAction.NewPay
            }

            is PayListEvent.ShowFAB -> {
                viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
            }

            is PayListEvent.ReloadScreen -> {
                getStatisticPay()
                loadPayList()
            }

            is PayListEvent.UndoDeleteClicked -> {
                viewState = viewState.copy(
                    items = viewState.items.map {
                        if (viewEvent.pay?.uidPay == it.uidPay) {
                            it.copy(isDelete = false, isOptionsRevealed = false)
                        } else
                           it
                    },
                    deletePays = null
                )
            }

            is PayListEvent.ShowSnackBar -> {
                viewState = viewState.copy(
                    messageSnackBar = viewEvent.detailsPayResult.message
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
                viewState.messageSnackBar = null
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

    private fun deletePayDB(pay: PayScreen) {
        CoroutineScope(
            context = Dispatchers.Default
        ).launch(exceptionHandlerPaysList) {
            val job = CoroutineScope(Dispatchers.IO).async {
                return@async payListInteractor.deletePay(pay = pay.toPay())
            }
            if (!job.await()) {
                viewState.isCanShowSnackBar = true
                viewState.messageSnackBar =
                    context.getString(R.string.error_save)
                viewState.isCanShowSnackBar = false
                viewState.withDismissAction = false
                viewState.messageSnackBar = null
                viewState.onDismissed = null
                viewState.onAction = null
                //resetStatusSnackBar()
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
                viewState = viewState.copy(
                    items = listPay.map {
                        //при удалении нескольких платежей список обновиться и тогда
                        // нужно проставть признак удаления иначе он будет затёрт из базы
                        if (viewState.deletePays?.uidPay == it.uidPay)
                            it.toPayScreen().copy(isDelete = true)
                        else
                            it.toPayScreen()
                    }
                )

            }
        }
    }

    fun setStatusSnackBar(
        isCanShowSnackBar: Boolean?,
        withDismissAction: Boolean = true,
        actionLabel: String? = null,
        messageSnackBar: String?,
        onAction: (() -> Unit)?,
        onDismissed: (() -> Unit)?,
    ) {
        isCanShowSnackBar?.let { isCan -> viewState = viewState.copy(isCanShowSnackBar = isCan) }
        viewState = viewState.copy(
            withDismissAction = withDismissAction,
            messageSnackBar = messageSnackBar,
            snackBarAction = actionLabel
        )
        onAction?.let { viewState.onAction = it }
        onDismissed?.let { viewState.onDismissed = it }
    }

 fun resetStatusSnackBar() {
        viewState.onAction = null
        viewState.onDismissed = null
        //   viewState = viewState.copy(isCanShowSnackBar = false, messageShackBar = null)
        viewState.isCanShowSnackBar = false
        //viewState.withDismissAction = true
        viewState.messageSnackBar = null
    }

     fun getStatisticPay() {
        Log.e("CLJR", "get Statistic PAY")
        val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val lastMonth =
            Clock.System.now().minus(DateTimePeriod(months = 1), TimeZone.currentSystemDefault())
                .toLocalDateTime(TimeZone.currentSystemDefault())
        CoroutineScope(Dispatchers.IO).launch {
            viewState = viewState.copy(incomePerMonth = payListInteractor.getStatisticPay(
                    viewState.beginDate,
                    viewState.endDate
                ),

                incomePerLastMonth =payListInteractor.getStatisticPay(
                    beginDate = "01.${lastMonth.formatRu(FormatDate.Month)}.${lastMonth.year}",
                    endDate = date.toDateTimeRuString().toString()
                ),
                incomePerYear = payListInteractor.getStatisticPay(
                    "01.01.${date.year}",
                    endDate = date.toDateTimeRuString(FormatDate.DateTime).toString()
                )
            )
        }
    }

}