package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.R
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

        when (viewEvent) {
            is SchedulerListEvent.AddMemberLesson->{
                // Добавление ученика в урок
                фывафвдлфыджол
            }
            is SchedulerListEvent.DeleteSwipe -> {
                //Удаление в расписании
                when (viewEvent.type) {
                    ItemType.lesson -> {//Если смахнули урок
                       viewEvent.scheduler?.startLesson?.let { startLesson ->
                           viewModelScope.launch {
                               schedulerInteract.deleteLesson(dayOfWeek = viewEvent.dayOfWeek,
                                   time = startLesson)
                           }
                       }
                    }

                    ItemType.client -> {
                        viewEvent.scheduler?.let { scheduler ->
                            viewModelScope.launch {
                                if (!schedulerInteract.deleteClient(scheduler)) {
                                    viewState.isCanShowSnackBar = true
                                    obtainEvent(
                                        viewEvent = SchedulerListEvent.ShowSnackBar(
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

            SchedulerListEvent.ReloadScheduler -> {
                loadData()
            }

            is SchedulerListEvent.ShowTimePiker -> {
                viewState = viewState.copy(
                    showStartTimePicker = viewEvent.show
                )
            }

            is SchedulerListEvent.NewClicked -> {
                viewState.selectDay = DayOfWeek.entries[viewEvent.index]
                viewState.isNewLesson = viewEvent.isNewLesson
                obtainEvent(SchedulerListEvent.ShowTimePiker(show = true))
            }

            is SchedulerListEvent.NewLesson -> {
                viewState.isCanShowSnackBar = true
                viewAction = SchedulerListAction.NewLesson
            }

            SchedulerListEvent.EditTime -> {
                //установка нового времени
                with(viewState) {
                    selectDay?.let {
                        if (oldTimeLesson != null && timeLesson != null) {
                            viewModelScope.launch {
                                schedulerInteract.editTime(
                                    dayOfWeek = it,
                                    oldTime = oldTimeLesson ?: 0,
                                    newTime = timeLesson ?: 0,
                                    duration = durationLesson ?: 0

                                )
                            }
                        }
                    }
                }
            }

            is SchedulerListEvent.SetTime -> {
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
            }

            is SchedulerListEvent.CollapseItem -> {
                viewState = viewState.copy(
                    dayList = viewState.dayList.mapIndexed { index, bool ->
                        if (index == viewEvent.index) !bool
                        else bool
                    }
                )
            }

            is SchedulerListEvent.ShowSnackBar -> {
                viewState = viewState.copy(
                    isShowSnackBar = viewEvent.showSnackBar,
                    messageShackBar = viewEvent.message
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
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
        Log.e("CLJR", "LoadData scheduler ${viewState.items}")
    }
}