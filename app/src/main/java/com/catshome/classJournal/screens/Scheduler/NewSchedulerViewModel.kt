package com.catshome.classJournal.screens.Scheduler

import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerAction
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerState
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewSchedulerViewModel @Inject constructor(private val interact: SchedulerInteract) :
    BaseViewModel<NewSchedulerState, NewSchedulerAction, NewSchedulerEvent>
        (installState = NewSchedulerState()) {

    override fun obtainEvent(viewEvent: NewSchedulerEvent) {
        TODO("Not yet implemented")
    }
}