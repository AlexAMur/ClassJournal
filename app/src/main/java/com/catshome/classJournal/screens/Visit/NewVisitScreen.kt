package com.catshome.classJournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.screens.PayList.ItemChildInSearch
import com.catshome.classJournal.screens.Visit.NewVisitViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.Visit.NewVisitAction
import com.catshome.classJournal.screens.Visit.NewVisitByScheduler
import com.catshome.classJournal.screens.Visit.NewVisitContent
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitScreen(
    viewModel: NewVisitViewModel = viewModel(),
    details: VisitDetails? = null,
) {
    val outerNavigation = localNavHost.current
    val viewAction by viewModel.viewActions().collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current
    val pageName = listOf("По расписанию", "Свободное")
    val initialPage = 0
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pageName.size }
    )
    Surface(
        Modifier
            .fillMaxSize(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ClassJournalTheme.colors.primaryBackground),
            verticalArrangement = Arrangement.Top
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick =
                        {
                            viewModel.obtainEvent(NewVisitEvent.CancelClicked)
                        }
                ) {
                    Icon(
                        Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                    )
                }
                Text(
                    stringResource(R.string.new_visit_dialog_headline),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.caption
                )
                TextButton(onClick = {
                    viewModel.obtainEvent(NewVisitEvent.SaveClicked)
                }
                ) {
                    Text(
                        stringResource(R.string.save_button),
                        color = ClassJournalTheme.colors.tintColor
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(all = 0.dp),
                pageSpacing = 0.dp,
                verticalAlignment = Alignment.Top,

                ) { page ->
                // Вычисляем индекс реального элемента через остаток от деления
                // Сам элемент (карточка на весь экран)

                Card(
                    modifier = Modifier
                        //.padding( 16.dp)
                        .fillMaxWidth()
                        .background(ClassJournalTheme.colors.secondaryBackground),
                    shape = ClassJournalTheme.shapes.cornersStyle,
                    colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground)

                ) {

                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Card(
                            Modifier
                                .fillMaxWidth(0.7f)
                            ,
                            shape = ClassJournalTheme.shapes.cornersStyle,
                            colors = CardDefaults.cardColors(
                                ClassJournalTheme.colors.controlColor
                            )
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                                    text = pageName[page],
                                    style = ClassJournalTheme.typography.body,
                                    color = ClassJournalTheme.colors.tintColor
                                )
                            }
                        }
                        if (page == 1)
                            NewVisitContent(viewModel)
                        else
                            NewVisitByScheduler(viewModel)
                    }
                }
                // }
            }
        }
        when (viewAction) {
            NewVisitAction.CloseScreen -> {
                keyboardController?.hide()
                viewModel.clearAction()
                outerNavigation.navigate(route = ItemScreen.VisitListScreen.name)
                {
                    popUpTo(ItemScreen.VisitListScreen.name) {
                        inclusive = true
                    }
                }
            }

            null -> {}
        }
    }
}
