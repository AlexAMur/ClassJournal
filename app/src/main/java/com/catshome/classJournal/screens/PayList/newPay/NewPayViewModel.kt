package com.catshome.classJournal.screens.PayList.newPay

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.Pay
import com.catshome.classJournal.domain.Pay.PayInteract
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Clock.System.now

@HiltViewModel
class NewPayViewModel @Inject constructor(
    private val payInteract: PayInteract
) :
    BaseViewModel<NewPayState, NewPayAction, NewPayEvent>
        (NewPayState()) {
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    private val exceptionHandlerPays = CoroutineExceptionHandler { coroutineContext, throwable ->
        viewState = viewState.copy(
            isChildError = true,
            isSnackBarShow = true,
            errorMessage = throwable.message.toString(),
            snackBarAction = context.getString(R.string.ok),
            onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
            onAction = { viewState = viewState.copy(isSnackBarShow = false) }
        )
        return@CoroutineExceptionHandler


        if (throwable.message?.contains("UID ребенка") == true) {
            viewState = viewState.copy(
                isChildError = true,
                isSnackBarShow = true,
                errorMessage = throwable.message.toString(),
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
                onAction = { viewState = viewState.copy(isSnackBarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("нулевым или отрицательным.") == true) {
            viewState = viewState.copy(
                isPayError = true,
                payError = throwable.message ?: "Error!!!"
            )
            return@CoroutineExceptionHandler
        }

        if (throwable.message?.contains("SQLITE_CONSTRAINT_UNIQUE") == true) {
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = context?.getString(R.string.error_unique_child) ?: "Ошибка!!!",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
                onAction = { viewState = viewState.copy(isSnackBarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true) {
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = " ${context?.getString(R.string.error_primarykey_group)}",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackBarShow = false },
                onAction = { viewState.isSnackBarShow = false }
            )
            return@CoroutineExceptionHandler
        }
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = "${context?.getString(R.string.error_save)} ${throwable.message} ",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackBarShow = false },
                onAction = { viewState.isSnackBarShow = false }
            )
            return@CoroutineExceptionHandler

    }

    override fun obtainEvent(viewEvent: NewPayEvent) {
        when (viewEvent) {
            NewPayEvent.ClearClicked->{
                viewState = viewState.copy(
                    searchText = TextFieldValue(""),
                    listChild = null,
                    selectChild = null
                )
            }
            is NewPayEvent.SetState -> {
                viewState = viewState.copy(
                    selectChild = MiniChild(
                        uid = viewEvent.pay.uidPay,
                        fio = "${viewEvent.pay.name} ${viewEvent.pay.surName}",
                        name = viewEvent.pay.name,
                        surname = viewEvent.pay.surName
                    ),
                    searchText = TextFieldValue("${viewEvent.pay.name} ${viewEvent.pay.surName}"),
                    pay = viewEvent.pay,
                    payment = TextFieldValue(
                        text = viewEvent.pay.payment.toString(),
                        selection = TextRange(viewEvent.pay.payment.toString().length)),
                    isChildError = false,
                    childErrorMessage = null,
                    indexFocus = -1,
                    isSurnameError = false,
                    isSnackBarShow = false,
                    snackBarAction = "",
                    isPayError = false,
                    payError = "",
                    errorMessage = ""
                )
            }

            NewPayEvent.ResetState -> {
                resetState()
                viewState.isResetState = false
            }

            NewPayEvent.CancelClicked -> {
                viewAction = NewPayAction.CloseScreen
            }

            NewPayEvent.SaveClicked -> {
                try {
                    if (viewState.selectChild == null) {
                        viewState = viewState.copy(
                            isChildError = true,
                            childErrorMessage = context.getString(R.string.search_child_error)
                        )
                    }
                    try {
                        viewState =
                            viewState.copy(pay = viewState.pay.copy(payment = viewState.payment.text.toInt()))
                    } catch (e: NumberFormatException) {
                        //viewState = viewState.copy(pay = viewState.pay.copy(payment = 0))
                        viewState = viewState.copy(
                            isPayError = true,
                            payError = context.getString(R.string.error_invalid_value) )
                        return
                    }
                    if (viewState.pay.payment <= 0) {
                        viewState = viewState.copy(
                            isPayError = true,
                            payError = context.getString(R.string.paymant_error)
                        )
                    }
                    if (viewState.isChildError || viewState.isPayError)
                        return          //для подсветки сразу всех ошибок в интерфейсе
                } catch (e: NumberFormatException) {
                    viewState = viewState.copy(
                        isPayError = true,
                        payError = context.getString(R.string.error_invalid_value)
                    )
                    return
                }
                CoroutineScope(Dispatchers.IO).launch(exceptionHandlerPays) {
                     viewState.selectChild?.let { child ->
                         val job = CoroutineScope(Dispatchers.IO).async(exceptionHandlerPays) {
                             return@async payInteract.savePay(
                                 uid = child.uid,
                                 payment = viewState.pay
                             )
                         }
                         if (job.await())
                             viewAction = NewPayAction.Successful
                         else {
                             viewState = viewState.copy(
                                 isSnackBarShow = true,
                                 errorMessage = context.getString(R.string.error_save),
                                 snackBarAction = context.getString(R.string.ok),
                                 onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
                                 onAction = { viewState = viewState.copy(isSnackBarShow = false) }
                             )
                         }
                     }
                }
            }

            is NewPayEvent.Search -> {
                viewState = viewState.copy(searchText = TextFieldValue(viewEvent.searchText))
                if (viewState.searchText.text.isEmpty()) {
                    viewState = viewState.copy(listChild = null)
                    return
                } else {
                    if (viewState.isChildError) {
                        viewState = viewState.copy(isChildError = false, childErrorMessage = null)
                    }
                }
                viewModelScope.launch {
                    payInteract.searchChild(viewEvent.searchText).collect {
                        if (it.isNullOrEmpty()) {
                            viewState =
                                viewState.copy(
                                    listChild = listOf(
                                        MiniChild(
                                            uid = "",
                                            fio = "пусто"
                                        )
                                    )
                                )
                            return@collect
                        }
                        it.let {
                            viewState = viewState.copy(listChild = it)
                        }
                    }
                }
            }
            NewPayEvent.onFocusePrice->{
              viewState =viewState.copy(payment =  viewState.payment.copy(
                        selection = TextRange(
                            start = 0,
                            end = viewState.payment.text.length
                        )
                )
              )
            }

            is NewPayEvent.SelectedChild -> {
                viewState = viewState.copy(
                    searchText = TextFieldValue(viewEvent.child.fio),
                    selectChild = viewEvent.child,
                    listChild = null
                )
            }
        }
    }

    private fun resetState() {
        viewState = viewState.copy(
            selectChild = null,
            searchText = TextFieldValue(),
            pay = Pay(
                datePay = now().toDateTimeRuString().toString(),
                payment = 0,
            ),
            isChildError = false,
            childErrorMessage = null,
            indexFocus = -1,
            isSurnameError = false,
            isSnackBarShow = false,
            snackBarAction = "",
            isPayError = false,
            payError = "",
            payment = TextFieldValue("0", TextRange(0, 1)),
            errorMessage = ""
        )
    }

    fun datePayChange(newDate: String?) {
        newDate?.let {
            viewState = viewState.copy(pay = viewState.pay.copy(datePay = newDate))
        }
    }
    fun paymentChange(newValue: String) {
        viewState = viewState.copy(
            payment = viewState.payment.copy(text = newValue,
                selection = if (newValue==viewState.payment.text)
                    TextRange(0, newValue.length)
                            else
                    TextRange( newValue.length)
            ),

        isPayError = false)
        //viewState = viewState.copy(isPayError = false)
    }
}