package com.catshome.classJournal.screens.Scheduler

import androidx.compose.material3.TimePickerState
import androidx.room.Index
import kotlin.time.Duration

sealed class SchedulerListEvent {
    data object NewClicked: SchedulerListEvent()
    data class NewLesson(val time: Long, val duration: Long): SchedulerListEvent()
    data class CollapseItem(val index: Int): SchedulerListEvent()
    data class ShowSnackBar(val message: String): SchedulerListEvent()
    data class ShowTimePiker(val show: Boolean): SchedulerListEvent()
}