package com.catshome.classJournal.screens.PayList

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayItem
import com.catshome.classJournal.domain.PayList.PayListInteractor
import com.catshome.classJournal.screens.group.GroupAction
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayListViewModel @Inject constructor(private val playListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, PayListAction, PayListEvent>(
        installState = PayListState(
            items = listOf(
                PayItem(surname = "Miron", datePay = "01.01.2025", payment = "500"),
                PayItem(surname = "Antov", datePay = "11.01.2025", payment = "1500"),
                PayItem(surname = "Vika", datePay = "31.01.2025", payment = "100"),
                PayItem(surname = "Semen", datePay = "11.01.2025", payment = "8500"),
            )
        )
    ) {
    override fun obtainEvent(viewEvent: PayListEvent) {
        when (viewEvent) {
            is PayListEvent.DeleteClicked -> {
                playListInteractor.deletePay(viewState.items[viewState.index])
            }
            PayListEvent.NewClicked -> {
             viewAction = PayListAction.NewPay
            }
            is PayListEvent.ShowFAB -> {
                if (viewEvent.isShowFAB) {
                    viewModelScope.launch {
                        delay(100)
                    }
                }
                viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
            }
            is PayListEvent.ReloadScreen -> loadPayList()
            is PayListEvent.UndoDeleteClicked -> {
              //TODO emplemention undelete pay
            }
        }
    }

    fun loadPayList() {
        viewModelScope.launch {
            playListInteractor.getPays()?.collect { listPay ->
               // viewState = viewState.copy(items = listPay)
            }
        }
    }
}