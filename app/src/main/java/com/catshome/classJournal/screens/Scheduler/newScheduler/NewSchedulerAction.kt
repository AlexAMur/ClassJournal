package com.catshome.classJournal.screens.Scheduler.newScheduler

sealed class NewSchedulerAction {
    data object  CloseScreen: NewSchedulerAction()
    data object  Save: NewSchedulerAction()
}