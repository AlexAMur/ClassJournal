package com.catshome.classJournal.screens.Scheduler

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import java.util.Collections.emptyMap

data class SchedulerListState (
    var showTimePicker: Boolean = false,
    var initTimeHour: Int =0,
    var initTimeMin: Int =0,
    var showDialog: Boolean =  false,
    val messageDialog: String?= null,
    val dialogHandler: String?= null,
    val messageShackBar: String?= null,
    val snackBarAction: String? =null,
    var onAction: (()->Unit)? = null,
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val dayList: List<Boolean> = listOf(false,false,false,false,false,false,false),
    var selectDay: DayOfWeek? = null,
    var oldTimeLesson: Int? = null,
    var timeLesson: Int?  = null,
    var durationLesson: Int = 45,
    val items: MutableMap<String, List<Scheduler>?> = emptyMap(),
    var onConfirm: (Int, Int)-> Unit = {_,_->},
    var onDialogConfirm: ()->Unit = {}
)
