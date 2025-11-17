package com.catshome.classJournal.screens.PayList

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayInteract
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPayViewModel @Inject constructor(
    private val payInteract: PayInteract
) :
    BaseViewModel<NewPayState, NewPayAction, NewPayEvent>
        (installState = NewPayState(pay = Pay())) {
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }


    override fun obtainEvent(viewEvent: NewPayEvent) {
        when (viewEvent) {
            NewPayEvent.CancelClicked -> {
                viewAction = NewPayAction.CloseScreen
            }
            NewPayEvent.SaveClicked -> {
                try {
                    viewState.pay.payment.toInt()
                    if (viewState.pay.payment.toInt() < 0) {
                        viewState = viewState.copy(
                            isPayError = true,
                            PayError = context.getString(R.string.paymant_error)
                        )
                        return
                    }
                } catch (e: NumberFormatException) {
                    viewState = viewState.copy(
                        isPayError = true,
                        PayError = context.getString(R.string.error_invalid_value)
                    )
                    return
                }
                viewState.selectChild?.let {child->
                    viewModelScope.launch() {
                        payInteract.savePay(
                            child = child,
                            payment = viewState.pay
                        )
                    }
                    viewAction = NewPayAction.Successful
                }
            }

            is NewPayEvent.Search -> {
                viewState = viewState.copy(searchText = viewEvent.searchText)
                if (viewState.searchText.isEmpty()) {
                    viewState = viewState.copy(listChild = null)
                    return
                }
                viewModelScope.launch {
                    payInteract.searchChild(viewEvent.searchText)?.collect {
                        if (it.isNullOrEmpty()) {
                            viewState =
                                viewState.copy(listChild = listOf(Child(uid = "", name = "пусто")))
                            return@collect
                        }

                        it?.let {
                            viewState = viewState.copy(listChild = it)
                        }

                    }
                }
            }

            is NewPayEvent.SelectedChild -> {
                viewState = viewState.copy(
                    searchText = "${viewEvent.child.name} ${viewEvent.child.surname}",
                    selectChild = viewEvent.child,
                    listChild = null
                )
            }
        }
    }

    fun datePayChange(newDate: String) {
        viewState = viewState.copy(pay = viewState.pay.copy(datePay = newDate))
    }

    fun paymentChange(newValue: String) {
        viewState = viewState.copy(pay = viewState.pay.copy(payment = newValue))
        if (viewState.pay.payment.isNotEmpty())
            viewState = viewState.copy(isPayError = false)
    }
}