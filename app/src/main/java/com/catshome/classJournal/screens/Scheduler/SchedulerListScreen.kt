package com.catshome.classJournal.screens.Scheduler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.communs.TimePikerDialog
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PayListEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SchedulerListScreen(
    navController: NavController,
    viewModel: SchedulerListViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
//    LaunchedEffect(Unit) {
//        CoroutineScope(Dispatchers.Default).launch {
//            viewModel.obtainEvent(SchedulerListEvent.ShowFAB(false))
//            delay(100)
//            viewModel.obtainEvent(SchedulerListEvent.ShowFAB(true))
//        }
//    }
//    DisposableEffect(Unit) {
//        onDispose {
//            viewModel.obtainEvent(SchedulerListEvent.ShowFAB(false))
//        }
//    }
    schedulerContent(viewModel)

    when(viewAction){
        SchedulerListAction.NewClick -> {
            navController.navigate(ItemScreen.NewSchedulerScreen.name)
            viewModel.clearAction()
        }
        SchedulerListAction.NewLesson->{

        }
        null -> {}
    }
}