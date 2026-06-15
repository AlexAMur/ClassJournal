package com.catshome.classJournal.screens.PayList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.ActionIcon
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.SwipeableItemWithActions
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.context
import com.catshome.classJournal.resource.R

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
                LaunchedEffect(viewState.messageSnackBar) {
                    if (!viewState.messageSnackBar.isNullOrEmpty() && viewState.isCanShowSnackBar) {
//                        CoroutineScope(Dispatchers.IO).launch {
                        SnackBarAction(
                            message = viewState.messageSnackBar.toString(),
                            actionLabel = viewState.snackBarAction,
                            snackBarState = sbHostState,
                            withDismissAction = viewState.withDismissAction,
                            onDismissed = {
                                viewState.onDismissed?.let { it() }
                                viewState.isCanShowSnackBar = false
                                viewState.messageSnackBar = null
                            },
                            onActionPerformed = {
                                viewState.onAction?.let { it() }
                                viewState.isCanShowSnackBar = false
                                viewState.messageSnackBar = null

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
            val columVertStateScroll = rememberScrollState()
            Column(
                Modifier
                    .background(ClassJournalTheme.colors.primaryBackground)
            ) {

                Card(
                    Modifier
                        .background(ClassJournalTheme.colors.primaryBackground)
                        .statusBarsPadding(),
                    colors = CardDefaults.cardColors(
                        containerColor = ClassJournalTheme.colors.secondaryBackground,
                        contentColor = ClassJournalTheme.colors.primaryText
                    )
                ) {
                    Column(
                        Modifier
                            .background(ClassJournalTheme.colors.primaryBackground)
                            .fillMaxWidth()
                            .padding(0.dp)
                            .clickable {
                                viewModel.obtainEvent(PayListEvent.onCollapse(true))
                            }
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
                                .background(ClassJournalTheme.colors.primaryBackground)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                                if (viewState.beginDate.isNotEmpty()) {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 16.dp),
                                        color = ClassJournalTheme.colors.tintColor,
                                        text = "c ${viewState.beginDate}"
                                    )
                                }
                                Text(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f),
                                    color = ClassJournalTheme.colors.tintColor,
                                    text = if (viewState.selectedOption != 4) " по ${viewState.endDate}" else
                                        "${stringResource(R.string.filter_all)} время"
                                )
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(R.drawable.arrow_drop_down_48),
                                contentDescription = "",
                                tint = ClassJournalTheme.colors.tintColor
                            )
                        }
                    }
                }
                HorizontalDivider(color = ClassJournalTheme.colors.disableColor)
                val state = rememberPullToRefreshState()
                Column(
                    Modifier
                        .background(ClassJournalTheme.colors.primaryBackground)
//                        .verticalScroll(columVertStateScroll)
                ) {
                    PullToRefreshBox(
                        isRefreshing = viewModel.isRefreshing,
                        state = state,
                        contentAlignment = Alignment.TopCenter,
                        onRefresh = {
                            if (!viewModel.isRefreshing)
                                viewModel.getStatisticPay()
                        },
                        indicator = {
                            Indicator(
                                containerColor = ClassJournalTheme.colors.primaryBackground,
                                isRefreshing = viewModel.isRefreshing,
                                color = ClassJournalTheme.colors.tintColor,
                                state = state,
                            )
                        }
                    ) {

                        StatisticPayCard(
                            incomePerMonth = viewState.incomePerMonth,
                            lastMonth = viewState.incomePerLastMonth,
                            totalYear = viewState.incomePerYear,
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                    color = ClassJournalTheme.colors.disableColor
                )
                if (viewState.items.isEmpty())
                    payScreenNoItems(
                        bottomPadding = padValues.calculateBottomPadding()
                    )
                else {
                    LazyColumn(
                        modifier = Modifier
                            .background(ClassJournalTheme.colors.primaryBackground)
                            .fillMaxSize()
                            .padding(
                                top = 8.dp,
                                bottom = paddingValues
                            ),
                        state = rememberLazyListState()
                    ) {
                        itemsIndexed(viewState.items) { index, item ->
                            if (!item.isDelete) {
                                Card(
                                    Modifier
                                        .background(ClassJournalTheme.colors.primaryBackground)
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, bottom = 8.dp, end = 16.dp),
                                    colors = CardDefaults.cardColors(
                                        ClassJournalTheme.colors.secondaryBackground
                                    ),
                                    shape = ClassJournalTheme.shapes.cornersStyle
                                )

                                {
                                    SwipeableItemWithActions(
                                        modifier = Modifier.background(
                                            ClassJournalTheme.colors.primaryBackground
                                        ),
                                        isRevealed = item.isOptionsRevealed,
                                        onExpanded = {
                                            //действие для удаления
//                                            ActionIcon(
//                                                onClick = {
                                            viewState.snackBarAction =
                                                context.getString(R.string.bottom_yes)
                                            viewState.withDismissAction = false
                                            viewModel.obtainEvent(
                                                viewEvent = PayListEvent.DeleteClicked(item)
                                            )
//                                                },
//                                                icon = Icons.Default.Delete,
//                                                modifier = Modifier
//                                                    .fillMaxHeight()
//                                                    .width(120.dp)
//                                            )
//
//                                            viewModel.obtainEvent(
//                                                PayListEvent.ChangeRevealed(
//                                                    index = index,
//                                                    isOptionsRevealed = true
//                                                )
//                                            )
                                        },
                                        onCollapsed = {
                                            viewModel.obtainEvent(
                                                PayListEvent.ChangeRevealed(
                                                    index = index,
                                                    isOptionsRevealed = false
                                                )
                                            )

                                        },
                                        actions = {
                                            //действие для удаления
                                            ActionIcon(
                                                onClick = {
                                                    viewState.snackBarAction =
                                                        context.getString(R.string.bottom_yes)
                                                    viewState.withDismissAction = false
//                                                    viewModel.obtainEvent(
//                                                        viewEvent = PayListEvent.DeleteClicked(item)
//                                                    )
                                                },
                                                icon = Icons.Default.Delete,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(120.dp)
                                            )
                                        },
                                    ) { offset ->
                                        itemPay(
                                            fio = viewState.items[index].fio,
                                            date = viewState.items[index].datePay,
                                            offset = Offset(x = offset, y = 0f),
                                            payment = viewState.items[index].payment.toString()
                                        ) {
                                            viewModel.obtainEvent(
                                                viewEvent = PayListEvent.UpdateClicked(
                                                    item
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}