package com.catshome.classJournal.screens.Visit.byScheduler

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.DialogScreen
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.getNow
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitViewModel
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitByScheduler(
    viewModel: NewVisitViewModel
) {
    val viewState by viewModel.viewState().collectAsState()
    val bottomPadding =
        LocalSettingsEventBus.current.currentSettings.collectAsState().value.innerPadding
            .calculateBottomPadding()
    // Для эффекта бесконечности используем очень большое число
    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage + getNow().dayOfWeek.ordinal,
        pageCount = { Int.MAX_VALUE } // "Бесконечное" количество страниц
    )
    if (viewState.isShowDialog) {
        viewState.onDismissDialog?.let {onDismissed->
            DialogScreen(
                title = viewState.errorMessage,
                text = viewState.errorMessage,
                onDismiss = onDismissed,
                dissmissText = "ОК",
                textContentColor = ClassJournalTheme.colors.errorColor,
            )
        }
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        border = BorderStroke
            (
            width = 2.dp,
            color = ClassJournalTheme.colors.disableColor
        )
        //.verticalScroll(state = rememberScrollState()),
        //colors = CardDefaults.cardColors(ClassJournalTheme.colors.disableContentColor)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp), // Элемент на весь экран
            pageSpacing = 0.dp
        ) { index ->
            val page = index % DayOfWeek.entries.size
            viewState.dateOnPage = Clock.System.now().minus(
                value = viewState.pageDayOfWeekOffset - index + getNow().dayOfWeek.ordinal,
                unit = DateTimeUnit.DAY,
                timeZone = TimeZone.currentSystemDefault()
            ).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            //if (pagerState.currentPage- index!=0)
            //  viewModel.obtainEvent(NewVisitEvent.ChangePageIndex())

//            if((pagerState.currentPage- index)+pagerState.currentPageOffsetFraction ==0.0f) {
//                Log.e("CLJR", "Page Launched ${viewState.pageIndex}")
//                viewModel.obtainEvent(NewVisitEvent.getScheduler(DayOfWeek.entries[index % DayOfWeek.entries.size]))
//            }
//            LaunchedEffect(viewState.pageIndex) {
//                Log.e("CLJR", "page index ${viewState.pageIndex}")
//                viewModel.obtainEvent(
//                    NewVisitEvent.getScheduler(dayOfWeek = DayOfWeek.entries[viewState.pageIndex])
//                )
//            }
            Column(
                Modifier
                    .fillMaxWidth()
                    //.background(ClassJournalTheme.colors.controlColor)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(ClassJournalTheme.colors.tintColor)
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "${DayOfWeek.entries[page].nameRu} ${
                            viewState.dateOnPage?.toDateTimeRuString(
                                formatDate = FormatDate.Date
                            )
                        } ",
                        color = ClassJournalTheme.colors.primaryText,
                        fontSize = ClassJournalTheme.typography.toolbar.fontSize,
                    )
                }
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(ClassJournalTheme.colors.primaryBackground)
                    //.padding(8.dp)
                ) {
                    if (viewState.scheduler.isNotEmpty()) {
                        viewState.scheduler.let { listScheduler ->
                            listScheduler[page]?.entries?.forEachIndexed { indexKey, mapScheduler ->

                                stickyHeader {
                                    ItemNewVisitHeader(
                                        header = mapScheduler.key,
                                        isChecked = if (viewState.lessonChecked[page].isNotEmpty())
                                            viewState.lessonChecked[page][indexKey]
                                        else
                                            false
                                    ) {
                                        viewModel.obtainEvent(
                                            viewEvent = NewVisitEvent.LessonClicked(
                                                dayInt = page,
                                                key = mapScheduler.key,
                                                isCheck = !viewState.lessonChecked[page][indexKey]
                                            )
                                        )
                                    }
                                }
                                mapScheduler.value?.let { listvisit ->
                                    itemsIndexed(listvisit) { itemIndex, scheduler ->
                                        Card(
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            colors = CardDefaults.cardColors(
                                                ClassJournalTheme.colors.secondaryBackground
                                            )
                                        )
                                        {
                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 16.dp)
                                            ) {
                                                Row(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .padding(end = 24.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(8.dp),
                                                        text = scheduler.fio,
                                                        color = ClassJournalTheme.colors.primaryText
                                                    )
                                                    Icon(
                                                        painter =
                                                            painterResource(
                                                                if (scheduler.check == true)
                                                                    R.drawable.box_ckeck
                                                                else
                                                                    R.drawable.box_out
                                                            ),
                                                        modifier = Modifier
                                                            .clickable(onClick = {
                                                                viewModel.obtainEvent(
                                                                    NewVisitEvent.ItemCheckClicked(
                                                                        dayInt = page,
                                                                        key = mapScheduler.key,
                                                                        indexItem = itemIndex,
                                                                        isCheck = !scheduler.check
                                                                    )
                                                                )
                                                            }),
                                                        contentDescription = "",
                                                        tint = ClassJournalTheme.colors.primaryText
                                                    )
                                                }
                                                TextField(
                                                    modifier = Modifier.Companion,
                                                    value = scheduler.priceScreen ?: "0",
                                                    label = stringResource(R.string.visit_price),
                                                    supportingText = "",//if (isPriceError) errorPriceMessage else "",
                                                    onValueChange = {
                                                        viewModel.obtainEvent(
                                                            NewVisitEvent.ChangePriceOnScheduler(
                                                                key = mapScheduler.key,
                                                                dayInt = page,
                                                                index = itemIndex,
                                                                text = it,
                                                            )
                                                        )
                                                    },
                                                    trailingIcon = null,
                                                    minLines = 1,
                                                    singleLine = true,
                                                    keyboardOptions = KeyboardOptions.Default.merge(
                                                        KeyboardOptions(keyboardType = KeyboardType.Number)
                                                    ),
                                                    errorState = false,// isPriceError,
                                                    readOnly = false
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
}