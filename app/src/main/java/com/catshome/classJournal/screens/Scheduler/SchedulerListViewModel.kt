package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulerListViewModel @Inject constructor(
    private val schedulerInteract: SchedulerInteract
) :
    BaseViewModel<SchedulerListState, SchedulerListAction, SchedulerListEvent>
        (
        installState = SchedulerListState()

    ) {

    override fun obtainEvent(viewEvent: SchedulerListEvent) {
        loadData()
        when (viewEvent) {
            is SchedulerListEvent.ShowTimePiker -> {
                viewState = viewState.copy(
                    showStartTimePicker = viewEvent.show,
                    newScheduler = Scheduler(
                        uid = null,
                        dayOfWeek = viewEvent.day?.shortName ?: "",
                        dayOfWeekInt = viewEvent.day?.ordinal ?: 0,
                    )
                )
            }

            SchedulerListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewClick
            }

            is SchedulerListEvent.NewLesson -> {
                viewState = viewState.copy(
                    items = viewState.items.toMutableMap().plus(
                        Pair(
                            first = viewEvent.day.shortName,
                            second = listOf(
                                Scheduler(
                                    dayOfWeek = viewEvent.day.shortName,
                                    dayOfWeekInt = viewEvent.day.ordinal,
                                    uidChild = null,
                                    uidGroup = null,
                                    name = "",
                                    startLesson = viewEvent.time,
                                    duration = viewEvent.duration
                                )
                            )
                        )
                    )
                )
                viewAction = SchedulerListAction.NewLesson
            }

            is SchedulerListEvent.SetTime -> {
                viewState.newScheduler?.let { scheduler ->
                    obtainEvent(
                        SchedulerListEvent.NewLesson(
                            day = DayOfWeek.entries[scheduler.dayOfWeekInt],
                            time = viewEvent.time,
                            duration = viewEvent.duration,
                        )
                    )
                }
            }

            is SchedulerListEvent.CollapseItem -> {
                viewState = viewState.copy(
                    dayList = viewState.dayList.mapIndexed { index, bool ->
                        if (index == viewEvent.index) !bool
                        else bool
                    }
                )
            }

            is SchedulerListEvent.ShowSnackBar -> TODO()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            schedulerInteract.getScheduler()?.collect { schedulerList ->
                viewState = viewState.copy(
                    items =
                        schedulerList.sortedBy { it.dayOfWeekInt }.groupBy {
                            it.dayOfWeek
                        })
            }
        }
    }
}