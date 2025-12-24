package com.catshome.classJournal.screens.PayList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.context
import com.catshome.classJournal.navigate.DetailsPay

@Composable
fun PayListContent(
    viewState: PayListState,
    viewModel: PayListViewModel,
) {
    val sbHostState = remember { SnackbarHostState() }
    val paddingValues = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
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
                LaunchedEffect(viewState.isShowSnackBar) {
                    if (viewState.isShowSnackBar && viewState.isCanShowSnackBar) {
                        //    keyboardController?.hide()
                        SnackBarAction(
                            message = viewState.messageShackBar?:"",
                            actionLabel = viewState.snackBarAction
                                ?: context.getString(R.string.ok),
                            sbHostState,
                            onDismissed = viewState.onDismissed ?: {
                                viewModel.obtainEvent(PayListEvent.ShowSnackBar(DetailsPay(false, "")))
                            },
                            onActionPerformed = viewState.onAction ?: {
                                viewModel.obtainEvent(PayListEvent.ShowSnackBar(DetailsPay(false, "")))
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
                            bottom = paddingValues + 16.dp,
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
            Column(
                Modifier
                    .background(ClassJournalTheme.colors.primaryBackground)
            ) {
                Card(
                    Modifier.statusBarsPadding(),
                    colors = CardDefaults.cardColors(
                        containerColor = ClassJournalTheme.colors.secondaryBackground,
                        contentColor = ClassJournalTheme.colors.primaryText
                    )
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                    {
                        viewState.selectChild?.let {
                            if (viewState.selectChild.fio.isNotEmpty())
                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 8.dp),
                                color = ClassJournalTheme.colors.tintColor,
                                text = viewState.selectChild.fio
                            )
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.obtainEvent(PayListEvent.onCollapse(true))
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            viewState.beginDate?.let {
                                if (it.isNotEmpty()) {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 16.dp),
                                        color = ClassJournalTheme.colors.tintColor,
                                        text = "c ${viewState.beginDate}"
                                    )
                                }
                            }
                            viewState.endDate?.let {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f),
                                    color = ClassJournalTheme.colors.tintColor,
                                    text = if (viewState.endDate.isNotEmpty()) " по ${viewState.endDate}" else
                                        "${stringResource(R.string.filter_all)} время"
                                )
                            }
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(R.drawable.arrow_drop_down_48),
                                contentDescription = "",
                                tint = ClassJournalTheme.colors.tintColor
                            )
                        }
                    }
                }
                if (viewState.items.isEmpty())
                    payScreenNoItems(
                        bottomPadding = padValues.calculateBottomPadding()
                    )
                else {
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
                        itemsIndexed(viewState.items) { index, item ->
                            itemPay(
                                fio = "${viewState.items[index].surName} ${viewState.items[index].name}",
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