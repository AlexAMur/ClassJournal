package com.catshome.classJournal.screens.PayList

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.PayListInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayListViewModel @Inject constructor(private val payListInteractor: PayListInteractor) :
    BaseViewModel<PayListState, PayListAction, PayListEvent>(
        installState = PayListState()
    ) {
        init {
            Log.e("CLJR", "viewModel - $this")
        }
    fun beginDateChange(newValue: String) {
        viewState = viewState.copy(beginDate = newValue)
    }
    fun endDateChange(newValue: String) {
        viewState = viewState.copy(endDate = newValue)
    }
    fun Search(newValue: String) {
        viewState = viewState.copy(searchText = newValue)
       // if (viewState.selectChild.name.isNotEmpty())
            //viewState = viewState.copy(isNameError = false)
    }

    override fun obtainEvent(viewEvent: PayListEvent) {
        Log.e("CLJR", "ViewModel obtainEvent")
        when (viewEvent) {
            is PayListEvent.DeleteClicked -> {
                Log.e("CLJR", "ViewModel delete")
                payListInteractor.deletePay(viewState.items[viewState.index])
            }

            is PayListEvent.NewClicked -> {
                viewAction = PayListAction.NewPay
            }

            is PayListEvent.ShowFAB -> {
                viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
            }

            is PayListEvent.ReloadScreen -> {
                Log.e("CLJR", "ViewModel ReloadScreen")
                loadPayList()
            }

            is PayListEvent.UndoDeleteClicked -> {
                //TODO emplemention undelete pay
            }

            is PayListEvent.ShowSnackBar -> {
                Log.e("CLJR", "ViewModel show snackbar")
                viewState = viewState.copy(
                    isShowSnackBar = viewEvent.isShow,
                    messageShackBar = viewEvent.message
                )
            }

            is PayListEvent.onCollapse ->{
                viewState = viewState.copy(filterCollapse = viewEvent.isCollapse)
            }

            is PayListEvent.Search -> { //тут отбираем список детей
                viewState = viewState.copy(searchText = viewEvent.searchText)
                if (viewState.searchText.isEmpty()) {
                    viewState = viewState.copy(selectChild = null)
                    return
                }
//                else {
//                    if (viewState.isChildError) {
//                        viewState = viewState.copy(isChildError = false, ChildErrorMessage = null)
//                    }
//                }
                viewModelScope.launch {
                    payListInteractor.searchChild(viewEvent.searchText).collect {
                        if (it.isNullOrEmpty()) {
                            viewState =
                                viewState.copy(listSearch = listOf(MiniChild(uid = "", name = "пусто")))
                            return@collect
                        }
                        it.let {
                            viewState = viewState.copy(listSearch = it)
                        }

                    }
                }
            }

            is PayListEvent.isFilterChildClicked -> {
                viewState =viewState.copy(isFilterChild = !viewState.isFilterChild)
            }
            is PayListEvent.isFilterDataClicked -> {
                viewState =viewState.copy(isFilterData =!viewState.isFilterData)
            }
        }
    }

    fun loadPayList() {
         viewModelScope.launch {
             payListInteractor.getPays()?.collect { listPay ->
                 viewState = viewState.copy(items = listPay)
             }
         }
    }
}