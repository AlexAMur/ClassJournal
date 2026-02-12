package com.catshome.classJournal.screens.PayList

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayInteract
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewPayViewModel @Inject constructor(
    private val payInteract: PayInteract
) :
    BaseViewModel<NewPayState, NewPayAction, NewPayEvent>
        (
        installState = NewPayState(
            pay = Pay(
                datePay = LocalDateTime.now().toDateTimeRuString()
            )
        )
    ) {
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    private val exceptionHandlerPays = CoroutineExceptionHandler { coroutineContext, throwable ->
        if (throwable.message?.contains("UID ребенка") == true) {
            viewState = viewState.copy(
                isChildError = true,
                isSnackbarShow = true,
                errorMessage = throwable.message.toString(),
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackbarShow = false) },
                onAction = { viewState = viewState.copy(isSnackbarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("нулевым или отрицательным.") == true) {
            viewState = viewState.copy(
                isPayError = true,
                PayError = throwable.message ?: "Error!!!"
            )
            return@CoroutineExceptionHandler
        }

        if (throwable.message?.contains("SQLITE_CONSTRAINT_UNIQUE") == true) {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = context?.getString(R.string.error_unique_child) ?: "Ошибка!!!",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackbarShow = false) },
                onAction = { viewState = viewState.copy(isSnackbarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true) {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = " ${context?.getString(R.string.error_primarykey_group)}",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackbarShow = false },
                onAction = { viewState.isSnackbarShow = false }
            )
            return@CoroutineExceptionHandler
        } else {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = "${context?.getString(R.string.error_save)} ${throwable.message} ",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackbarShow = false },
                onAction = { viewState.isSnackbarShow = false }
            )
            return@CoroutineExceptionHandler
        }
    }

    override fun obtainEvent(viewEvent: NewPayEvent) {
        when (viewEvent) {
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
                            ChildErrorMessage = context.getString(R.string.search_child_error)
                        )
                    }

                    if (viewState.pay.payment.toInt() < 0) {
                        viewState = viewState.copy(
                            isPayError = true,
                            PayError = context.getString(R.string.paymant_error)
                        )
                    }
                    if (viewState.isChildError || viewState.isPayError)
                        return          //для подсветки сразу всех ошибок в интерфейсе
                } catch (e: NumberFormatException) {
                    viewState = viewState.copy(
                        isPayError = true,
                        PayError = context.getString(R.string.error_invalid_value)
                    )
                    return
                }
                viewState.selectChild?.let { child ->
                    viewModelScope.launch(exceptionHandlerPays) {
                        payInteract.savePay(
                            uid = child.uid,
                            payment = viewState.pay
                        )
                    }
                }
                viewAction = NewPayAction.Successful
            }

            is NewPayEvent.Search -> {
                viewState = viewState.copy(searchText = viewEvent.searchText)
                if (viewState.searchText.isEmpty()) {
                    viewState = viewState.copy(listChild = null)
                    return
                } else {
                    if (viewState.isChildError) {
                        viewState = viewState.copy(isChildError = false, ChildErrorMessage = null)
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

            is NewPayEvent.SelectedChild -> {
                viewState = viewState.copy(
                    searchText = viewEvent.child.fio,
                    selectChild = viewEvent.child,
                    listChild = null
                )
            }
        }
    }

    private fun resetState() {
        viewState = viewState.copy(
            selectChild = null,
            searchText = "",
            pay = Pay(datePay = LocalDateTime.now().toDateTimeRuString()),
            isChildError = false,
            ChildErrorMessage = null,
            indexFocus = -1,
            isSurnameError = false,
            isSnackbarShow = false,
            snackbarAction = "",
            isPayError = false,
            PayError = "",
            errorMessage = ""
        )
    }

    fun datePayChange(newDate: String?) {
        newDate?.let {
            viewState = viewState.copy(pay = viewState.pay.copy(datePay = newDate))
        }
    }

    fun paymentChange(newValue: String) {
        viewState = viewState.copy(pay = viewState.pay.copy(payment = newValue))
        if (viewState.pay.payment.isNotEmpty())
            viewState = viewState.copy(isPayError = false)
    }
}