package com.catshome.classJournal.screens.Visit.ListVisit

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Vignette
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.FilterScreen.FilterSetting
import com.catshome.classJournal.communs.FilterScreen.ScreenEnum
import com.catshome.classJournal.communs.ItemFAB
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.fabMenu
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.communs.formatRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.navigate.OptionFilterList
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.PayList.PayListAction
import com.catshome.classJournal.screens.Visit.FilterVisit.FilterListVisitHeider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun visitListScreen(
    navController: NavController,
    viewModel: VisitListViewModel = viewModel(),
    optionFilter: OptionFilterList? =null

) {
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
        LaunchedEffect(Unit) {
            if (optionFilter != null)
                viewModel.obtainEvent(VisitListEvent.SetFilter(optionFilter))
            else
                viewModel.obtainEvent(VisitListEvent.Reload)
        }
        Scaffold(
            Modifier
                .fillMaxWidth(),
            snackbarHost = {
                SnackbarHost(
                    hostState = sbHostState,
                    modifier = Modifier.padding(bottom = paddingValues)
                )
                LaunchedEffect(viewState.messageSnackBar) {
                    if (!viewState.messageSnackBar.isNullOrEmpty()) {// && viewState.isCanShowSnackBar) {

                        SnackBarAction(
                            message = viewState.messageSnackBar.toString(),
                            actionLabel = viewState.snackBarLabel,
                            snackBarState = sbHostState,
//                            withDismissAction = viewState.withDismissAction,
                            onDismissed = {
                                viewState.onDismissed?.let { it() }
                            },
                            onActionPerformed = {
                                viewState.onAction?.let { it() }
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
                                icon = painterResource(R.drawable.calendar_today_24),
                                onClick = {
                                    viewModel.obtainEvent(VisitListEvent.NewVisit)
                                }
                            )),
                        fabVisible = viewState.isShowFAB
                    )
                }
            }
        ) { padValues ->
            Surface(
                Modifier
                    .fillMaxSize()
                    .background(ClassJournalTheme.colors.primaryBackground),
                color = ClassJournalTheme.colors.primaryBackground
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .systemBarsPadding()
                        .padding(bottom = 2.dp)
                )
                {
                    FilterListVisitHeider(viewModel)
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        color = ClassJournalTheme.colors.disableColor
                    )

                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                            .padding(bottom = paddingValues),
                        state = rememberLazyListState()
                    ) {
                        viewState.listVisit?.forEach { key, visits ->
                            stickyHeader {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 8.dp,
                                            top = 8.dp,
                                            bottom = 16.dp,
                                            end = 8.dp
                                        ),
                                    shape = ShapeDefaults.ExtraSmall,
                                    colors = CardDefaults.cardColors(
                                        containerColor = ClassJournalTheme.colors.disableColor,
                                        contentColor = ClassJournalTheme.colors.primaryText
                                    )
                                ) {
                                    Text(
                                        text = key,
                                        Modifier.padding(
                                            start = 16.dp,
                                            top = 16.dp,
                                            bottom = 16.dp
                                        ),
                                        color = ClassJournalTheme.colors.primaryText,
                                        fontStyle = ClassJournalTheme.typography.caption.fontStyle
                                    )
                                }
                            }
                            visits?.forEachIndexed { index, visit ->

                                item(key = visit?.uid) {
                                    visit?.uid?.let { uid ->
                                        if (visit.isDelete == false) {

                                            ItemListVisits(
                                                visit = visit,
                                                onClick = {
                                                    viewModel.obtainEvent(
                                                        VisitListEvent.EditVisit(
                                                            uidVisit = uid
                                                        )
                                                    )
                                                },
                                                onDeleteItem = { visit ->
                                                    viewModel.obtainEvent(
                                                        viewEvent = VisitListEvent.DeleteVisit
                                                            (
                                                            key = key,
                                                            uidVisit = visit.uid ?: ""
                                                        )
                                                    )
                                                }
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
                        price = visit.price,
                        pageIndex = 1 // страница редактирования
                    )
                )
                viewModel.clearAction()
            }
        }

        VisitListAction.OpenFilter -> {
            navController.navigate(
                FilterSetting(
                    childId = viewState.selectChild?.uid,
                    childFIO = viewState.selectChild?.fio,
                    optionsIndex = viewState.selectedOption,
                    sortEnum = viewState.sortValue,
                    beginDate = viewState.beginDate?.formatRu(),//"01.${LocalDateTime.now().month.value}.${LocalDateTime.now().year}",
                    endDate = viewState.endDate?.formatRu(),//LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1)
                    screen = ScreenEnum.VisitListScreen
                )
            )
            viewModel.clearAction()
        }

        null -> {}
    }
}