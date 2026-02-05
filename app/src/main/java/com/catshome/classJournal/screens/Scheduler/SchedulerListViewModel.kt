package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
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

            is SchedulerListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewState.selectDay = DayOfWeek.entries[viewEvent.index]
                viewState.isNewLesson = viewEvent.isNewLesson
                obtainEvent(SchedulerListEvent.ShowTimePiker(show = true))
                // viewAction = SchedulerListAction.NewClick
            }

            is SchedulerListEvent.NewLesson -> {
                Log.e("CLJR", "NewLesson")
                viewAction = SchedulerListAction.NewLesson
            }

            SchedulerListEvent.EditTime -> {
                //установка нового времени
                Log.e("CLJR", "edittime")
                with(viewState) {
                    selectDay?.let {
                        if (oldTimeLesson != null && timeLesson!= null) {
                            viewModelScope.launch {
                                schedulerInteract.editTime(
                                    dayOfWeek = it,
                                    oldTime = oldTimeLesson?:0,
                                    newTime = timeLesson?:0,
                                    duration = durationLesson?:0

                                )
                            }
                        }
                    }
                }
            }

            is SchedulerListEvent.SetTime -> {
                Log.e("CLJR", "Settime")
                if (viewState.durationLesson != viewEvent.duration) {
                    viewState.oldDurationLesson = viewState.durationLesson
                    viewState.durationLesson = viewEvent.duration
                } else
                    viewState.oldDurationLesson = null

                if (!viewState.isNewLesson || viewState.oldDurationLesson != null) {
                    //обновить время для всех записей со старым зачение времени
                    viewState.oldTimeLesson = viewState.timeLesson
                    viewState.timeLesson = viewEvent.time
                    obtainEvent(SchedulerListEvent.EditTime)
                } else {  //добавляем новое занятие
                    viewState.oldTimeLesson = null
                    viewState.timeLesson = viewEvent.time
                    obtainEvent(SchedulerListEvent.NewLesson)
                }


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
            schedulerInteract.getScheduler(null)?.collect { schedulerList ->
                viewState = viewState.copy(
                    items =
                        schedulerList.sortedBy { it.dayOfWeekInt }.groupBy {
                            it.dayOfWeek
                        })
            }
        }
    }
}