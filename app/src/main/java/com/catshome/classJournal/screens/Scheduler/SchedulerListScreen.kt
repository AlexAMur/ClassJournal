package com.catshome.classJournal.screens.Scheduler

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.TimePikerDialog
import com.catshome.classJournal.context
import com.catshome.classJournal.navigate.DetailsPay
import com.catshome.classJournal.navigate.NewLesson
import com.catshome.classJournal.navigate.SaveLesson
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PayListEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun SchedulerListScreen(
    navController: NavController,
    viewModel: SchedulerListViewModel = viewModel(),
    saveLesson: SaveLesson? = null
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current
    val sbHostState = remember { SnackbarHostState() }
    saveLesson?.let { save ->

        viewModel.obtainEvent(
            SchedulerListEvent.ShowSnackBar(
                showSnackBar = save.isShowSnackBar, save.message
            )
        )
    }
    LaunchedEffect(Unit) {
        viewModel.obtainEvent(SchedulerListEvent.ReloadScheduler)
    }

    schedulerContent(viewModel)

    when (viewAction) {
        SchedulerListAction.NewLesson -> {
            with(viewState) {
                keyboardController?.hide()
                viewState.selectDay?.let { day ->
                    viewState.timeLesson?.let { timeLesson ->
                        navController.navigate(
                            NewLesson(
                                dayOfWeek = day,
                                startTime = timeLesson,
                                duration = durationLesson,
                                isEdit = false
                            )
                        )
                    }
                }
            }
            viewModel.clearAction()
        }
        null -> {}
    }
}