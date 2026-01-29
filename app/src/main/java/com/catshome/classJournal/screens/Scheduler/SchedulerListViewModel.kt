package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
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
                viewState = viewState.copy(showStartTimePicker = viewEvent.show)
            }

            SchedulerListEvent.NewClicked -> {
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewClick
            }

            is SchedulerListEvent.NewLesson -> {
                viewAction = SchedulerListAction.NewLesson
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
            //schedulerInteract.getScheduler()?.collect {

            //  viewState = viewState.copy(
            val items =
                schedulerInteract.getScheduler().sortedBy { it.dayOfWeekInt }.groupBy { s ->
                    s.dayOfWeek
                }

            repeat(items.size) {
                items.keys.forEach {key->
                    Log.e("CLJR", "key${key}")
                    var startTime = 0L
                    items[key]?.forEachIndexed { index, list ->
//                        if(index==0){
//                            Log.e("CLJR", "     ${list.startLesson.toString()}")
//                            startTime = list.startLesson
//                        }

                        if(startTime!=list.startLesson){
                            Log.e("CLJR", "     ${list.startLesson.toString()}")
                            startTime = list.startLesson
                        }
                          Log.e("CLJR", list.toString())
                    }
                }
            }

            //)
            //}
        }
    }
}