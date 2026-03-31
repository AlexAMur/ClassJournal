package com.catshome.classJournal.screens.Visit

import android.net.TetheringManager
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
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = bottomPadding + 8.dp)
            .verticalScroll(state = rememberScrollState()),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.primaryBackground)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp), // Элемент на весь экран
            pageSpacing = 0.dp
        ) { index ->
            val pageIndex = index % items.size
            Row(
                Modifier.fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = items[pageIndex].nameRu,
                    color = ClassJournalTheme.colors.primaryText,
                    fontSize = ClassJournalTheme.typography.caption.fontSize,
                )
            }
            Text(
                "Ввод посещения по расписанию.",
                color = ClassJournalTheme.colors.primaryText
            )
        }
    }
}