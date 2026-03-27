package com.catshome.classJournal.screens.Visit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.PayList.PayListEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.uuid.Uuid


@Composable
fun visitListScreen(navController: NavController, viewModel: VisitListViewModel = viewModel()) {
    val viewAction by viewModel.viewActions().collectAsState(null)
    val viewState by viewModel.viewState().collectAsState()
    val sbHostState = remember { SnackbarHostState() }

    val paddingValues = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.obtainEvent(VisitListEvent.ShowFAB(false))
            delay(100)
            viewModel.obtainEvent(VisitListEvent.ShowFAB(true))
        }
    }
    Surface(
        Modifier
            .fillMaxWidth(),
    ) {
        Scaffold(
            Modifier
                .fillMaxWidth(),
            snackbarHost = {
                SnackbarHost(
                    hostState = sbHostState,
                    modifier = Modifier.padding(bottom = paddingValues)
                )

//            LaunchedEffect(viewState.messageShackBar) {
//                if (!viewState.messageShackBar.isNullOrEmpty() && viewState.isCanShowSnackBar) {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        SnackBarAction(
//                            message = viewState.messageShackBar.toString(),
//                            actionLabel = viewState.snackBarAction,
//                            snackBarState = sbHostState,
//                            withDismissAction = viewState.withDismissAction,
//                            onDismissed = {
//                                viewState.onDismissed?.let { it() }
//                                viewState.isCanShowSnackBar = false
//                                viewState.messageShackBar = null
//                            },
//                            onActionPerformed = {
//                                viewState.onAction?.let { it() }
//                                viewState.isCanShowSnackBar = false
//                                viewState.messageShackBar = null
//
//                            }
//                        )
//                    }
//                }
//            }
            },
            bottomBar = {
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    fabMenu(
                        listFAB = listOf(
                            ItemFAB(
                                containerColor = ClassJournalTheme.colors.tintColor,
                                contentColor = ClassJournalTheme.colors.secondaryBackground,
                                icon = painterResource(R.drawable.rusrub_48),//R.drawable.pay),
                                onClick = {
                                    viewModel.obtainEvent(VisitListEvent.NewVisit)
                                }
                            )),
                        fabVisible = viewState.isShowFAB
                    )
                    FloatingActionButton(
                        modifier = Modifier.padding(
                            bottom = paddingValues + 16.dp,
                            end = 16.dp
                        ),
                        onClick = {
                        viewModel.obtainEvent(VisitListEvent.NewVisit)
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.calendar), ""
                        )
                    }
                }
            }
        ) { padValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(ClassJournalTheme.colors.primaryBackground)
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .padding(bottom = paddingValues)
                    //.background(ClassJournalTheme.colors.)

                ) {
                    items(0) {
                        ItemVisitContent()
                    }

                }

            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.obtainEvent(VisitListEvent.ShowFAB(false))
        }
    }

    when (viewAction) {
        VisitListAction.NewVisit -> {
            navController.navigate(
                VisitDetails(
                    uid = UUID.randomUUID().toString()
                )
            )
        }
        is VisitListAction.EditVisit -> {
            (viewAction as VisitListAction.EditVisit).visit.let { visit ->
                navController.navigate(
                    VisitDetails(
                        uid = visit.uid,
                        uidChild = visit.uidChild,
                        fio = visit.fio,
                        date = visit.data,
                        price = visit.price
                    )
                )
            }
        }
            null -> {}
        }
    }