package com.catshome.classJournal.screens.Visit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID


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
                                icon =   painterResource(R.drawable.calendar_today_24),
                                onClick = {
                                    viewModel.obtainEvent(VisitListEvent.NewVisit)
                                }
                            )),
                        fabVisible = viewState.isShowFAB
                    )
                }
            }
        ) { padValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(ClassJournalTheme.colors.primaryBackground)
            ) {
                TextField(
                    modifier = Modifier,
                    value = "",
                    label = stringResource(R.string.visit_price),
                    supportingText = null,
                    onValueChange = {},
                    trailingIcon = null,
                    minLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.merge(
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    ),
                    errorState = true,
                    readOnly = false
                )
//                LazyColumn(
//                    Modifier
//                        .fillMaxSize()
//                        .systemBarsPadding()
//                        .padding(bottom = paddingValues)
//                    //.background(ClassJournalTheme.colors.)
//
//                ) {
//                    items(0) {
//                       // ItemVisitContent()
//                    }
//
//                }
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
            viewModel.clearAction()
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
                        uid = visit.uid!!,
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