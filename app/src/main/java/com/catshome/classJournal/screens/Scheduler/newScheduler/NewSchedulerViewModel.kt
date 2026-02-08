package com.catshome.classJournal.screens.Scheduler.newScheduler

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.domain.Scheduler.mapToScheduler
import com.catshome.classJournal.screens.PayList.NewPayEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerEvent.*
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSchedulerViewModel @Inject constructor(private val interact: SchedulerInteract) :
    BaseViewModel<NewSchedulerState, NewSchedulerAction, NewSchedulerEvent>
        (installState = NewSchedulerState()) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
                viewState = viewState.copy(itemsList = interact.getListClient("%%"))

        }
    }

    override fun obtainEvent(viewEvent: NewSchedulerEvent) {
        when (viewEvent) {
            NewSchedulerEvent.CloseEvent -> viewAction = NewSchedulerAction.CloseScreen
            NewSchedulerEvent.SaveEvent -> {
                viewState.itemsList?.let { list ->
                    viewState.dayOfWeek?.let { dayOfWeek ->
                        viewModelScope.launch {
                            interact.saveScheduler(
                                dayOfWeek = dayOfWeek,
                                time = viewState.startTime.toLong(),
                                duration = viewState.duration,
                                list = list
                            )
                        }
                    }
                }
                viewAction = NewSchedulerAction.Save
            }

            is NewSchedulerEvent.Search -> {
                viewState = viewState.copy(searchText = viewEvent.search)
                CoroutineScope(Dispatchers.IO).launch {
                        viewState =
                            viewState.copy(itemsList = interact.getListClient("%${viewEvent.search}%"))
                }
            }

            NewSchedulerEvent.ClearSearch -> {
                obtainEvent(Search(""))
            }

            is NewSchedulerEvent.Checked -> {
                viewState = viewState.copy(
                    itemsList =
                        viewState.itemsList?.mapIndexed { index, scheduler ->
                            if (viewEvent.index == index)
                                scheduler.copy(isChecked = !scheduler.isChecked)
                            else
                                scheduler
                        }
                )
            }
        }
    }
}