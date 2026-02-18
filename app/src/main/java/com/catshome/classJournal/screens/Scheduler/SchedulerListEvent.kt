package com.catshome.classJournal.screens.Scheduler

import android.content.Context
import android.os.Message
import androidx.compose.material3.TimePickerState
import androidx.room.Index
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlin.time.Duration

sealed class SchedulerListEvent {
    //при нажатии на кнопку новый урок
    data class NewClicked(val index: Int,val  isNewLesson: Boolean): SchedulerListEvent()
    data class DeleteSwipe(val type: ItemType,
                           val dayOfWeek: DayOfWeek,
                           val key: String, val scheduler: Scheduler?,val  context: Context): SchedulerListEvent()
    data object NewLesson: SchedulerListEvent()//(val day: DayOfWeek, val time: Int, val duration: Int): SchedulerListEvent()
    data object EditTime: SchedulerListEvent()
    data object ReloadScheduler: SchedulerListEvent()
    //получение данных из диалога времени
    data class SetTime(val time: Int, val duration: Int): SchedulerListEvent()
    data class CheckTimeLesson( val dayOfWeek: DayOfWeek?,val time: Int, val duration: Int): SchedulerListEvent()
    data class AddMemberLesson(val dayOfWeek: DayOfWeek,val time: Int, val duration: Int): SchedulerListEvent()
    data class CollapseItem(val index: Int): SchedulerListEvent()
    data class ShowSnackBar(val showSnackBar: Boolean, val message: String? =null): SchedulerListEvent()
    //запуск и скрытие диалога времени
    data class ShowTimePiker(val show: Boolean,): SchedulerListEvent()
}