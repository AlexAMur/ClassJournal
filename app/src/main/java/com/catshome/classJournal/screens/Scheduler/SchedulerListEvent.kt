package com.catshome.classJournal.screens.Scheduler

import androidx.compose.material3.TimePickerState
import androidx.room.Index
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlin.time.Duration

sealed class SchedulerListEvent {
    data object NewClicked: SchedulerListEvent()
    data class NewLesson(val day: DayOfWeek, val time: Int, val duration: Int): SchedulerListEvent()
    data class SetTime(val time: Int, val duration: Int): SchedulerListEvent()
    data class CollapseItem(val index: Int): SchedulerListEvent()
    data class ShowSnackBar(val message: String): SchedulerListEvent()
    data class ShowTimePiker(val show: Boolean,): SchedulerListEvent()
}