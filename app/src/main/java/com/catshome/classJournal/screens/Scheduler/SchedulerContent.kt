package com.catshome.classJournal.screens.Scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.TimePikerDialog
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.screens.PayList.itemPay

@Composable
fun schedulerContent(viewModel: SchedulerListViewModel) {
    val viewState by viewModel.viewState().collectAsState()
    val sbHostState = remember { SnackbarHostState() }
    val paddingValues = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

    Scaffold(
        Modifier
            .fillMaxWidth(),
        snackbarHost = {
            SnackbarHost(
                hostState = sbHostState,
                modifier = Modifier.padding(bottom = paddingValues)
            )
            LaunchedEffect(viewState.isShowSnackBar) {
                if (viewState.isShowSnackBar && viewState.isCanShowSnackBar) {
                    //    keyboardController?.hide()
                    SnackBarAction(
                        message = viewState.messageShackBar,
                        actionLabel = viewState.snackBarAction
                            ?: context.getString(R.string.ok),
                        sbHostState,
                        onDismissed = viewState.onDismissed ?: {
                            //     viewModel.obtainEvent(SchedulerListEvent.ShowSnackBar(DetailsPay(false, "")))
                        },
                        onActionPerformed = viewState.onAction ?: {
                            //      viewModel.obtainEvent(SchedulerListEvent.ShowSnackBar(DetailsPay(false, "")))
                        }
                    )
                }
            }
        },

        ) { padValues ->

        Column(
            Modifier
                //.padding(bottom = padValues.calculateBottomPadding())
                .background(ClassJournalTheme.colors.primaryBackground)
        ) {
            Card(
                Modifier.statusBarsPadding(),
                colors = CardDefaults.cardColors(
                    containerColor = ClassJournalTheme.colors.secondaryBackground,
                    contentColor = ClassJournalTheme.colors.primaryText
                )
            ) {

//                    if (viewState.items.isEmpty())
//                        schedulerScreenNoItems(
//                            bottomPadding = padValues.calculateBottomPadding()
//                        )
//                    else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 8.dp,
                            bottom = paddingValues
                        )
                        .background(ClassJournalTheme.colors.primaryBackground),
                    state = rememberLazyListState()
                ) {

                    items(DayOfWeek.entries.toTypedArray()) { day ->
                        //itemsIndexed() { index, item ->
                        itemScheduler(
                            day = day.shortName,//"${viewState.items[index].name} ",
                            emptyList(),
                            newTime = {
                                viewModel.obtainEvent(SchedulerListEvent.NewLesson)
                            },
                            newMember = {
                                TODO("Добавить открытия окна выбора клиента")
                            }
                        )
                    }
                }
            }
            if (viewState.showStartTimePicker) {
                TimePikerDialog(
                    title = "Начало занятия",
                    onDismiss = { viewModel.obtainEvent(SchedulerListEvent.ShowTimePiker(false)) },
                    onConfirm = {}
                ) {

                }
            }
        }
    }
}


//bottomBar = {
//    Row(Modifier.fillMaxWidth(), Arrangement.End) {
//        fabMenu(
//            listFAB = listOf(
//                ItemFAB(
//                    containerColor = ClassJournalTheme.colors.tintColor,
//                    contentColor = ClassJournalTheme.colors.secondaryBackground,
//                    icon = painterResource(R.drawable.outline_add_card_24),//R.drawable.pay),
//                    onClick = {
//                        viewModel.obtainEvent(SchedulerListEvent.NewClicked)
//                    }
//                )),
//            fabVisible = viewState.showFAB
//        )
//        FloatingActionButton(
//            modifier = Modifier.padding(
//                bottom = paddingValues + 16.dp,
//                end = 106.dp
//            ),
//            onClick = { viewModel.obtainEvent(SchedulerListEvent.NewClicked) }) {
//            Icon(
//                painter = painterResource(R.drawable.outline_add_card_24), ""
//            )
//        }
//    }
//}