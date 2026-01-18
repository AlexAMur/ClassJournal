package com.catshome.classJournal.screens.Scheduler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SchedulerList(
    //optionFilter: OptionFilterPaysList? = null,
    navController: NavController,
    viewModel: SchedulerListViewModel = viewModel()
    ) {
        val viewState by viewModel.viewState().collectAsState()
        val viewAction by viewModel.viewActions().collectAsState(null)
        schedulerContent()
}