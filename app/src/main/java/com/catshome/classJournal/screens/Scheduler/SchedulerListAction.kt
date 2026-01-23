package com.catshome.classJournal.screens.Scheduler

sealed class SchedulerListAction {
    data object NewClick: SchedulerListAction()
    data object NewLesson: SchedulerListAction()
}