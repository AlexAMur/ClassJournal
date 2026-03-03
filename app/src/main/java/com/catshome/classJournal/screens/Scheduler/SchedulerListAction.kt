package com.catshome.classJournal.screens.Scheduler

sealed class SchedulerListAction {
    data object NewLesson: SchedulerListAction()
    data object NewLesson1: SchedulerListAction()
}