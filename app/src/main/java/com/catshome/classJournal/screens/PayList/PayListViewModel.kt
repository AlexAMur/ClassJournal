package com.catshome.classJournal.screens.PayList

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayListInteractor
import com.catshome.classJournal.screens.group.GroupAction
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayListViewModel @Inject constructor(private val playListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, GroupAction, PayListEvent>(installState = PayListState(
        items = listOf(Pay(nameSurname = "Miron", datePay = "01.01.2025", payment = 500),
            Pay(nameSurname = "Antov", datePay = "11.01.2025", payment = 1500),
            Pay(nameSurname = "Vika", datePay = "31.01.2025", payment = 100),
            Pay(nameSurname = "Semen", datePay = "11.01.2025", payment = 8500),
            )
    )) {
    override fun obtainEvent(viewEvent: PayListEvent) {
        when (viewEvent){
            is PayListEvent.DeleteClicked -> {
                playListInteractor.deletePay(viewState.items[viewState.index])
            }
            PayListEvent.NewClicked -> {

            }
            is PayListEvent.ReloadScreen -> loadPayList()
            is PayListEvent.UndoDeleteClicked ->{
                //playListInteractor.undeletePay()
            }
        }
    }

    fun loadPayList() {
        viewModelScope.launch {
            playListInteractor.getPays().collect { listPay ->
                viewState = viewState.copy(items = listPay)
            }
        }
    }
}