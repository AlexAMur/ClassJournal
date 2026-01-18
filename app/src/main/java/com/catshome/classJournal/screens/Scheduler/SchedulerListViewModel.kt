package com.catshome.classJournal.screens.Scheduler

import com.catshome.classJournal.Scheduler.SchedulerListAction
import com.catshome.classJournal.Scheduler.SchedulerListEvent
import com.catshome.classJournal.Scheduler.SchedulerListState
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
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
        TODO("Not yet implemented")
    }
}