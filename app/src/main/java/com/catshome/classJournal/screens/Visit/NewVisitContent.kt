package com.catshome.classJournal.screens.Visit

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.resource.R
import kotlinx.datetime.TimeZone
import java.util.Date
import kotlin.time.Clock

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitContent(
    viewModel: NewVisitViewModel,
) {
    val viewState by viewModel.viewState().collectAsState()
    val bottomPadding =
        LocalSettingsEventBus.current.currentSettings.collectAsState().value.innerPadding.calculateBottomPadding()

//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = bottomPadding + 8.dp)
//            .verticalScroll(state = rememberScrollState()),
//        colors = CardDefaults.cardColors(ClassJournalTheme.colors.primaryBackground),
//        border = BorderStroke(width = 2.dp, ClassJournalTheme.colors.tintColor )
//
//    ) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(ClassJournalTheme.colors.primaryBackground)
    ) {
        ItemVisitContent(
            fio = viewState.searchText,
            price =viewState.priceScreen,
            onValueChange = { inputText ->
                viewModel.obtainEvent(NewVisitEvent.Search(inputText))
            },
            isSearchError = viewState.isSearchError,
            isPriceError = viewState.isPriceError,
            date = viewState.visit?.data?.toLocalDateTimeRu()?.toLong()?.let { data ->
                Date(data)
            } ?: Date(Clock.System.now().toEpochMilliseconds()),
            isShowDateDialog = viewState.isShowDateDialog,
            showDateDialog = {
                viewModel.obtainEvent(NewVisitEvent.ShowDateDialog(it))
            },
            listChild = viewState.listChild,
            errorSearchMessage = viewState.searchErrorMessage,
            onPriceChange = { price ->
                viewModel.obtainEvent(NewVisitEvent.ChangePrice(price))
            },
            onClearSelect = { viewModel.obtainEvent(NewVisitEvent.ClearSelect) },
            onDateSelect = { date ->

                viewModel.obtainEvent(NewVisitEvent.SelectDate(date))
            },
        ) { child ->
            viewModel.obtainEvent(NewVisitEvent.SelectChild(child))
        }
    }

//        val modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
//        SearchField(
//            text = viewState.searchText,
//            label = stringResource(R.string.search_label),
//            isError = viewState.isChildError,
//            errorMessage = viewState.ChildErrorMessage,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
//                .focusRequester(viewModel.listTextField[0])
//                .onFocusChanged {
//                    if (it.isFocused)
//                        viewState.indexFocus = 0
//                },
//        ) { searchText ->
//            viewModel.obtainEvent(NewVisitEvent.Search(searchText = searchText))
//        }
//        viewState.listChild?.let { listChild ->
//            if (listChild.isNotEmpty()) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp, end = 16.dp)
//                        .offset(y = (-19).dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = ClassJournalTheme.colors.secondaryBackground,
//                        contentColor = ClassJournalTheme.colors.primaryText
//                    ),
//                    border = BorderStroke(2.dp, color = ClassJournalTheme.colors.tintColor),
//                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
//                )
//                {
//                    Row() {
//                        Text(
//                            text ="fdsa"
//
//                        )
//                    }
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 12.dp, bottom = 8.dp)
//                            .heightIn(min = 0.dp, max = 300.dp)
//                    ) {
//                        itemsIndexed(listChild) { index, child ->
//                            ItemChildInSearch(
//                                name = child.name,
//                                surname = child.surname,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        start = 16.dp,
//                                        top = 4.dp,
//                                        end = 16.dp,
//                                        bottom = 8.dp
//                                    ),
//                                style = ClassJournalTheme.typography.body,
//                                contentColor = ClassJournalTheme.colors.tintColor,
//                                onClicked = {
//
//                                },
//                            )
//                        }
//                    }
//                }
//            }
//        }

//}

}