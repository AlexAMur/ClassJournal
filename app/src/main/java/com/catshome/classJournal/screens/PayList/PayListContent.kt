package com.catshome.classJournal.screens.PayList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.FilterScreen
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.context

@Composable
fun PayListContent(
    viewState: PayListState,
    viewModel: PayListViewModel,
    ) {
    val sbHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val padValues = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Scaffold(
            Modifier
                .fillMaxWidth()
                .background(color = ClassJournalTheme.colors.primaryBackground),
            snackbarHost = {
                SnackbarHost(
                    hostState = sbHostState,
                    modifier = Modifier.padding(bottom = padValues)
                )
                LaunchedEffect(viewState.isShowSnackBar) {
                    if (viewState.isShowSnackBar) {
                        keyboardController?.hide()
                        SnackBarAction(
                            message = viewState.messageShackBar,
                            actionLabel = viewState.snackBarAction
                                ?: context.getString(R.string.ok),
                            sbHostState,
                            onDismissed = viewState.onDismissed ?: {
                                viewModel.obtainEvent(PayListEvent.ShowSnackBar(false, ""))
                            },
                            onActionPerformed = viewState.onAction ?: {
                                viewModel.obtainEvent(PayListEvent.ShowSnackBar(false, ""))
                            }

                        )
                    }
                }
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
                                    viewModel.obtainEvent(PayListEvent.NewClicked)
                                }
                            )),
                        fabVisible = viewState.showFAB
                    )
                    FloatingActionButton(
                        modifier = Modifier.padding(
                            bottom = padValues + 16.dp,
                            end = 16.dp
                        ),
                        onClick = { viewModel.obtainEvent(PayListEvent.NewClicked) }) {
                        Icon(
                            painter = painterResource(R.drawable.rusrub_24), ""
                        )
                    }
                }
            }
        ) { padValues ->
            Column(Modifier.fillMaxSize()
                .background(ClassJournalTheme.colors.primaryBackground)
                ) {
                FilterScreen(
                    viewModel,
                    )
                if (viewState.items.isEmpty())
                    payScreenNoItems(
                        bottomPadding = padValues.calculateBottomPadding()
                    )
                else {
                    Column(
                        Modifier
                            .background(ClassJournalTheme.colors.primaryBackground)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 24.dp, bottom = padValues.calculateBottomPadding())
                                .background(ClassJournalTheme.colors.primaryBackground),
                            state = rememberLazyListState()
                        ) {
                            itemsIndexed(viewState.items) { index, item ->
                                itemPay(
                                    fio = "${viewState.items[index].Name} ${viewState.items[index].Surname}",
                                    date = viewState.items[index].datePay,
                                    payment = viewState.items[index].payment
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}