package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Scheduler.SchedulerInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.screens.Scheduler.SchedulerListEvent.*
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SchedulerListViewModel @Inject constructor(
    private val schedulerInteract: SchedulerInteract
) :
    BaseViewModel<SchedulerListState, SchedulerListAction, SchedulerListEvent>
        (
        installState = SchedulerListState()
    ) {
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        when (throwable) {
            is IllegalArgumentException -> {
                viewState.isCanShowSnackBar = true
                obtainEvent(
                    ShowSnackBar(
                        showSnackBar = true,
                        message = throwable.message
                    )
                )
            }
        }
    }

    override fun obtainEvent(viewEvent: SchedulerListEvent) {
        when (viewEvent) {
            is ShowDialog -> {
                viewState = viewState.copy(
                    showDialog = viewEvent.isShowDialog,
                    dialogHandler = viewEvent.dialogHader,
                    messageDialog = viewEvent.message
                )
            }

            is SchedulerListEvent.AddMemberLesson -> {
                // Добавление ученика в урок
                viewState.selectDay = viewEvent.dayOfWeek
                viewState.timeLesson = viewEvent.time
                viewState.durationLesson = viewEvent.duration
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewLesson
            }

            is ShowTimePiker -> {
                Log.e(
                    "CLJR","Show timePiker"
                )
                viewState = viewState.copy(
                    // oldTimeLesson = viewEvent.time,
                    showTimePicker = viewEvent.show,
                    initTimeHour = viewEvent.time?.let { it / 60 } ?: Calendar.getInstance().get(
                        android.icu.util.Calendar.HOUR_OF_DAY
                    ),
                    initTimeMin = viewEvent.time?.let { it % 60 } ?: Calendar.getInstance().get(
                        android.icu.util.Calendar.MINUTE
                    ),
                )
            }

            is NewClicked -> {
                viewState.selectDay = DayOfWeek.entries[viewEvent.index]
                viewState.isNewLesson = viewEvent.isNewLesson
                viewState.oldTimeLesson = null
                viewState.oldDurationLesson = null
                //обработчик нажатия кнопки ок в диалоге выбора времени
                viewState.onConfirm = { time, duration ->
                    viewState.timeLesson = time
                    viewState.durationLesson = duration
                    viewState.showTimePicker = false
                    viewState.isCanShowSnackBar = true
                    viewAction = SchedulerListAction.NewLesson
                }
                obtainEvent(ShowTimePiker(show = true))
            }

            is EditTime -> {
                //установка нового времени

                with(viewState) {
                    Log.e(
                        "CLJR",
                        " Select day= $selectDay, oldTime = $oldTimeLesson,  time= $timeLesson"
                    )
                    viewState.onConfirm = { time, duration ->
                        viewState.showTimePicker =false
                        selectDay?.let { day ->
                            oldTimeLesson?.let { oldTime ->
                                viewModelScope.launch {
                                    schedulerInteract.editTime(
                                        dayOfWeek = day,
                                        oldTime = oldTime,
                                        newTime = time,
                                        duration = duration
                                    )
                                }
                            }
                        }
                    }

                }
              obtainEvent( ShowTimePiker(
                    show = true,
                   time= viewEvent.oldTime,
                  duration = viewEvent.duration
                ))
            }

            is ShowSnackBar -> {
                viewState = viewState.copy(
                    isShowSnackBar = viewEvent.showSnackBar,
                    messageShackBar = viewEvent.message
                )
            }

            is CollapseItem -> {
                viewState = viewState.copy(
                    dayList = viewState.dayList.mapIndexed { index, bool ->
                        if (index == viewEvent.index) !bool
                        else bool
                    }
                )
            }

            is DeleteSwipe -> {
                //Удаление в расписании
                when (viewEvent.type) {
                    ItemType.lesson -> {//Если смахнули урок
                        viewEvent.scheduler?.startLesson?.let { startLesson ->
                            viewModelScope.launch {
                                schedulerInteract.deleteLesson(
                                    dayOfWeek = viewEvent.dayOfWeek,
                                    time = startLesson
                                )
                            }
                        }
                    }

                    ItemType.client -> {
                        viewEvent.scheduler?.let { scheduler ->
                            viewModelScope.launch {
                                if (!schedulerInteract.deleteClient(scheduler)) {
                                    viewState.isCanShowSnackBar = true
                                    obtainEvent(
                                        viewEvent = ShowSnackBar(
                                            showSnackBar = true,
                                            message = viewEvent.context.getString(R.string.error_save)
                                        )
                                    )
                                } else
                                    viewState.items[viewEvent.key] =
                                        viewState.items[viewEvent.key]?.filter { it != scheduler }
                            }
                        }
                    }//Если смахнули только одну запись
                }
            }

            ReloadScheduler -> {
                loadData()
            }
        }
    }

    suspend fun checkTime(dayOfWeek: DayOfWeek, oldTime: Int?, time: Int, duration: Int): Boolean {
        return schedulerInteract.checkTimeLesson(
            dayOfWeek = dayOfWeek,
            oldTime = oldTime,
            startTime = time,
            duration = duration
        )
    }


    fun loadData() {
        viewModelScope.launch {
            schedulerInteract.getScheduler(null)?.collect { schedulerList ->
                viewState = viewState.copy(
                    items =
                        schedulerList.sortedBy { it.dayOfWeekInt }.toMutableList().groupBy {
                            it.dayOfWeek
                        }.toMutableMap()
                )
            }
        }
    }
}


//is SetTime -> {
//    viewState.durationLesson = viewEvent.duration
//    if (viewState.isNewLesson) {  //новое занятие
//        Log.e("CLJR", "time Newlesson ${viewEvent.time}")
//        viewState.oldTimeLesson = null
//        viewState.timeLesson = viewEvent.time
//
//    } else {
//        Log.e("CLJR", "time ${viewEvent.time}")
//        viewState.timeLesson = viewEvent.time
//        obtainEvent(viewEvent = EditTime)
//    }
//}