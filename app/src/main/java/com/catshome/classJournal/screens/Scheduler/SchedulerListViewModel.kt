package com.catshome.classJournal.screens.Scheduler

import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.screens.PayList.PayListAction
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchedulerListViewModel @Inject constructor(
    private val schedulerInteract: SchedulerInteract
):
    BaseViewModel<SchedulerListState, SchedulerListAction, SchedulerListEvent>
        (
        installState = SchedulerListState()
    ) {
    override fun obtainEvent(viewEvent: SchedulerListEvent) {
        when(viewEvent){
            SchedulerListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewClick
            }
            is SchedulerListEvent.ShowFAB -> { viewState = viewState.copy(showFAB = viewEvent.show)}
            is SchedulerListEvent.ShowSnackBar -> TODO()
        }
    }
}