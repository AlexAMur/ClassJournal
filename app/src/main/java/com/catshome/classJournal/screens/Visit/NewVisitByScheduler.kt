package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.domain.communs.DayOfWeek

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
    val initialPage = Int.MAX_VALUE / 1024
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE/512} // "Бесконечное" количество страниц
    )

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
//            Log.e("CLJR", "Index page ${index % DayOfWeek.entries.size}")
//            Log.e("CLJR", "CurPage ${pagerState.currentPage % DayOfWeek.entries.size}")
            val page = index % DayOfWeek.entries.size
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
                        // modifier = Modifier.fillMaxWidth(),
                        text = DayOfWeek.entries[page].nameRu,
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
                    if (!viewState.scheduler.isNullOrEmpty())
                    viewState.scheduler?.let { listScheduler ->
                        listScheduler[page]?.entries?.forEach{listScheduler->
                            stickyHeader {
                                ItemNewVisitHeader(
                                    header = listScheduler.key
                                ){
                                    viewModel.obtainEvent(NewVisitEvent.LessonClicked)
                                }
                            }

                            itemsIndexed(listScheduler.value) { index, scheduler ->
                                Card(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    colors = CardDefaults.cardColors(
                                        ClassJournalTheme.colors.secondaryBackground
                                    )
                                )
                                {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = scheduler.fio.toString(),
                                        color = ClassJournalTheme.colors.primaryText
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