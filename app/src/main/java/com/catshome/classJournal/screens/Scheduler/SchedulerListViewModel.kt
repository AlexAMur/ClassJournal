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

            is AddMemberLesson -> {
                // Добавление ученика в урок
                viewState.selectDay = viewEvent.dayOfWeek
                viewState.timeLesson = viewEvent.time
                viewState.durationLesson = viewEvent.duration
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewLesson
            }

            is ShowTimePiker -> {
                Log.e(
                    "CLJR", "Show timePiker"
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
                viewState.oldTimeLesson = null
                //viewState.oldDurationLesson = null
                obtainEvent(ShowTimePiker(show = true))
                //обработчик нажатия кнопки ок в диалоге выбора времени
                viewState.onConfirm = { time, duration ->
                    viewState.timeLesson = time
                    viewState.durationLesson = duration
                    viewState.isCanShowSnackBar = true
                    if (checkTime(
                            dayOfWeek = viewEvent.day,
                            oldTime = null,
                            time = time,
                            duration = duration
                        )
                    ) {// есть пересечение по времени
                        viewState = viewState.copy(showDialog = true)
                        viewState.onDialogConfirm =
                            { // проигнорировать предупреждение о пересечение нового занятия в расписании

                                viewState = viewState.copy(
                                    showTimePicker = false,
                                    showDialog = false
                                )
                                viewAction = SchedulerListAction.NewLesson
                            }
                    } else{ // нет пересечения нового занятия по времени
                        viewState = viewState.copy(showTimePicker = false)
                        viewAction = SchedulerListAction.NewLesson
                    }
                }

            }

            is EditTime -> {
                //установка нового времени
                with(viewState) {
                    viewState.onConfirm = { time, duration ->
                        selectDay?.let { day ->
                            oldTimeLesson?.let { oldTime ->
                                if (checkTime(
                                        dayOfWeek = day,
                                        oldTime = oldTime,
                                        time = time,
                                        duration = duration
                                    )
                                ) {// есть пересечение по времени
                                    viewState = viewState.copy(showDialog =true)
                                    viewState.onDialogConfirm = { //если да
                                        // закрываем окно выбора времени и сохраняем новое значение
                                        viewState = viewState.copy(
                                            showTimePicker = false,
                                            showDialog = false
                                        )
                                        viewModelScope.launch {
                                            schedulerInteract.editTime(
                                                dayOfWeek = day,
                                                oldTime = oldTime,
                                                newTime = time,
                                                duration = duration
                                            )
                                        }
                                    }

                                } else {//нет пересечения по времени просто сохраняем
                                    viewState = viewState.copy(showTimePicker = false)
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
                }
                obtainEvent(
                    ShowTimePiker(
                        show = true,
                        time = viewEvent.oldTime,
                        duration = viewEvent.duration
                    )
                )
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
                                }
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

    fun checkTime(dayOfWeek: DayOfWeek, oldTime: Int?, time: Int, duration: Int): Boolean {
//Проверка на время если oldTime null то это новый урок, иначе проверка без учета времени занятия
        // которое мы изменяем
        val Job = CoroutineScope(Dispatchers.IO).async {
            return@async schedulerInteract.checkTimeLesson(
                dayOfWeek = dayOfWeek,
                oldTime = oldTime,
                startTime = time,
                duration = duration
            )
        }
        return runBlocking {
            Job.await()
        }
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