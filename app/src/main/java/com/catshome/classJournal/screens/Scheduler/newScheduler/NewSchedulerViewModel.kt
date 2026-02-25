package com.catshome.classJournal.screens.Scheduler.newScheduler

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.domain.Scheduler.mapToScheduler
import com.catshome.classJournal.screens.PayList.NewPayEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerEvent.*
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSchedulerViewModel @Inject constructor(private val interact: SchedulerInteract) :
    BaseViewModel<NewSchedulerState, NewSchedulerAction, NewSchedulerEvent>
        (installState = NewSchedulerState()) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            viewState.dayOfWeek?.let { day ->
                viewState.startTime?.let { timeLesson ->
                    viewState = viewState.copy(
                        itemsList = interact.getListClient(
                            name = "%",
                            dayOfWeek = day,
                            startTimeLesson = timeLesson
                        )
                    )
                }
            }

        }
    }

    override fun obtainEvent(viewEvent: NewSchedulerEvent) {
        when (viewEvent) {
            ReloadClient -> {
                obtainEvent(NewSchedulerEvent.Search(viewState.searchText))
            }

            CloseEvent -> viewAction = NewSchedulerAction.CloseScreen

            SaveEvent -> {
                val list = viewState.itemsList?.filter { it.isChecked }
                list?.let {
                    viewModelScope.launch {
                        viewState.dayOfWeek?.let { dayOfWeek ->
                            val deferred = CoroutineScope(Dispatchers.IO).async {
                                interact.saveScheduler(
                                    dayOfWeek = dayOfWeek,
                                    time = viewState.startTime.toLong(),
                                    duration = viewState.duration,
                                    list = list
                                )
                                return@async true
                            }
                            if (deferred.await()) {
                                obtainEvent(ClearState)
                                viewAction = NewSchedulerAction.Save
                            }
                        }
                    }
                }
            }

            is Search -> {
                viewState = viewState.copy(searchText = viewEvent.search)
                CoroutineScope(Dispatchers.IO).launch {
                    viewState.dayOfWeek?.let { day ->
                        viewState.startTime?.let { lesson ->
                            viewState =
                                viewState.copy(
                                    itemsList = interact.getListClient(
                                        name = "%${viewEvent.search}%",
                                        dayOfWeek = day,
                                        startTimeLesson = lesson
                                    )
                                )
                        }
                    }
                }
            }

            ClearSearch -> {
                obtainEvent(Search(""))
            }

            is Checked -> {
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

            ClearState -> {
                viewState = viewState.copy(
                    itemsList = null,
                    dayOfWeek = null,
                    startTime = 0,
                    duration = 0,
                    searchText = ""
                )
            }
        }
    }
}