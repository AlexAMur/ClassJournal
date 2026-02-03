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
                    showStartTimePicker = viewEvent.show
                )
            }

            SchedulerListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewClick
            }

            is SchedulerListEvent.NewLesson -> {
                viewState.selectDay = viewEvent.day
                viewState.selectTime = viewEvent.time
                viewState.selectDuration = viewEvent.duration
                //obtainEvent(SchedulerListEvent.ShowTimePiker(true))
                viewAction = SchedulerListAction.NewLesson
            }

            is SchedulerListEvent.SetTime -> {


//                viewState.selectDay?.let { selectDay ->
//                    viewState = viewState.copy(
//                        items = viewState.items.toMutableMap().plus(
//                            Pair(
//                                first = selectDay.shortName,
//                                second = listOf(
//                                    Scheduler(
//                                        dayOfWeek = selectDay.shortName,
//                                        dayOfWeekInt = selectDay.ordinal,
//                                        uidChild = null,
//                                        uidGroup = null,
//                                        name = "",
//                                        startLesson = viewEvent.time,
//                                        duration = viewEvent.duration
//                                    )
//                                )
//                            )
//                        )
//                    )
//                }
                Log.e("CLJR", viewState.items.toString())
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