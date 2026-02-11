package com.catshome.classJournal.screens.Scheduler.newScheduler

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.Search.ItemWithCheck
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.navigate.DetailsPay
import com.catshome.classJournal.navigate.NewLesson
import com.catshome.classJournal.navigate.SaveLesson
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.ItemChildInSearch
import com.catshome.classJournal.screens.PayList.NewPayEvent
import com.catshome.classJournal.screens.Scheduler.newScheduler.NewSchedulerViewModel

@Composable
fun NewSchedulerScreen(
    navController: NavController,
    viewModel: NewSchedulerViewModel = viewModel(),
    newLesson: NewLesson? = null
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val stateList = rememberLazyListState(0)
    newLesson?.let {
        LaunchedEffect(
            viewState.dayOfWeek != it.dayOfWeek ||
                    viewState.startTime != it.startTime ||
                    viewState.duration != it.duration
        ) {
            if (viewState.dayOfWeek != it.dayOfWeek ||
                viewState.startTime != it.startTime ||
                viewState.duration != it.duration
            ) {
                viewState.dayOfWeek = it.dayOfWeek
                viewState.startTime = it.startTime
                viewState.duration = it.duration
                viewModel.obtainEvent(NewSchedulerEvent.ReloadClient)
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ClassJournalTheme.colors.disableColor
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize(),
            //        .background(ClassJournalTheme.colors.tintColor),
            //      shape = ClassJournalTheme.shapes.cornersStyle,
            colors = CardDefaults.cardColors(ClassJournalTheme.colors.primaryBackground),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
//-------------строка с кнопками---------------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ClassJournalTheme.colors.disableColor)
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { viewModel.obtainEvent(NewSchedulerEvent.CloseEvent) }) {
                        Icon(
                            Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                        )
                    }
                    Text(
                        stringResource(R.string.new_lesson_headline),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.caption
                    )
                    TextButton(onClick = { viewModel.obtainEvent(NewSchedulerEvent.SaveEvent) }) {
                        Text(
                            stringResource(R.string.save_button),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }

                }
//            HorizontalDivider(
//                modifier = Modifier.fillMaxWidth(),
//                color = ClassJournalTheme.colors.disableContentColor
//            )
//--------------Окно поиска---------------------------------
                SearchField(
                    text = viewState.searchText,
                    label = stringResource(R.string.search_label),
                    isError = false,
                    errorMessage = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
                        //.focusRequester(viewModel.listTextField[0])
                        .onFocusChanged {
                            //if (it.isFocused)
                            //      viewState.indexFocus = 0
                        },
                    onClickCancel = {
                        viewModel.obtainEvent(NewSchedulerEvent.ClearSearch)
                    }
                ) { searchText ->
                    viewModel.obtainEvent(NewSchedulerEvent.Search(search = searchText))
                }
//----------------------------------------------
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = ClassJournalTheme.colors.disableContentColor
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ClassJournalTheme.colors.primaryBackground)
                    .padding(top = 12.dp, bottom = 8.dp),
                state = stateList

            ) {
                viewState.itemsList?.let {
                    itemsIndexed(it) { index, child ->
                        ItemWithCheck(
                            item = child.name,
                            texStyle = ClassJournalTheme.typography.caption,
                            onClick = {
                                viewModel.obtainEvent(NewSchedulerEvent.Checked(index))
//                        if (child.uid.isNotEmpty())
//                            viewModel.obtainEvent(
//                                NewPayEvent.SelectedChild(
//                                    child
//                                )
//                            )
                            },
                            isChecked = child.isChecked,
                            startImage = if (child.uidChild.isNullOrEmpty())
                                painterResource(R.drawable.outline_group_24)
                            else
                                painterResource(R.drawable.outline_account_circle_48),
                        )
                    }
                }
            }
        }
    }
    when (viewAction) {
        NewSchedulerAction.CloseScreen -> {
            viewModel.clearAction()

            navController.navigate(
                SaveLesson(
                    isShowSnackBar = false,
                    message = ""
                )
            )
        }

        NewSchedulerAction.Save -> {
            navController.navigate(
                SaveLesson(
                    isShowSnackBar = true,
                    message = stringResource(R.string.save_successful)
                )
            )
//            {
//                popUpTo(ItemScreen.PayListScreen.name) {
//                    inclusive = true
//                }
//            }
            //viewState.isResetState = true
            viewModel.clearAction()

        }

        null -> {}
    }
}