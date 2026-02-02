package com.catshome.classJournal.screens.Scheduler

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerAction
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerState
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSchedulerViewModel @Inject constructor(private val interact: SchedulerInteract) :
    BaseViewModel<NewSchedulerState, NewSchedulerAction, NewSchedulerEvent>
        (installState = NewSchedulerState()) {

    override fun obtainEvent(viewEvent: NewSchedulerEvent) {
        when (viewEvent) {
            NewSchedulerEvent.CloseEvent -> {}
            NewSchedulerEvent.SaveEvent -> {}
            is NewSchedulerEvent.Search -> {
                viewModelScope.launch {
                 viewState = viewState.copy(itemsList = interact.getListClient())
                }
            }
        }

    }
}