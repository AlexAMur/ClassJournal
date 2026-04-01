package com.catshome.classJournal.screens.Visit

import android.R
import android.net.TetheringManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.IMultiInstanceInvalidationCallback
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
    val items = DayOfWeek.entries
    // Для эффекта бесконечности используем очень большое число
    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE } // "Бесконечное" количество страниц
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = bottomPadding + 8.dp),
            //.verticalScroll(state = rememberScrollState()),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.disableContentColor)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp), // Элемент на весь экран
            pageSpacing = 0.dp
        ) { index ->
            val pageIndex = index % items.size
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(ClassJournalTheme.colors.controlColor)
                    .padding(  4.dp),
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
                        text = items[pageIndex].nameRu,
                        color = ClassJournalTheme.colors.primaryText,
                        fontSize = ClassJournalTheme.typography.toolbar.fontSize,
                    )
                }
                val lesson = listOf("item 1", "item 2", "item 3")
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(ClassJournalTheme.colors.primaryBackground)
                        .padding(8.dp)
                ) {
                    items(lesson) {
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
                                text =  it,
                                color = ClassJournalTheme.colors.primaryText
                                )
                        }
                    }
                }
//                Text(
//                    "Ввод посещения по расписанию.",
//                    color = ClassJournalTheme.colors.primaryText
//                )
            }
        }
    }
}