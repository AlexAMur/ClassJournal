package com.catshome.classJournal.communs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateStringRU
import com.catshome.classJournal.screens.PayList.NewPayEvent
import com.catshome.classJournal.screens.PayList.PayListEvent
import com.catshome.classJournal.screens.PayList.PayListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FilterScreen(viewModel: PayListViewModel, modifier: Modifier = Modifier) {
    val viewState by viewModel.viewState().collectAsState()
    Card(
        modifier = modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp)
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground)
    ) {

        if (viewState.filterCollapse) {//свернуто
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("fdsakfsa")
                Icon(
                    modifier = Modifier
                        .width(48.dp)
                        .clickable {
                            viewModel.obtainEvent(PayListEvent.onCollapse(false))
                        },
                    painter = painterResource(R.drawable.arrow_drop_down_48),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.tintColor
                )
            }
        } else { //раскрыто
            Column(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Icon(
                        painter = if (viewState.isFilterChild) painterResource(R.drawable.box_ckeck) else painterResource(
                            R.drawable.box_out
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 8.dp, end = 16.dp)
                            .clickable {
                                viewModel.obtainEvent(PayListEvent.isFilterChildClicked)
                            }
                    )
                    Text(
                        text = stringResource(R.string.filter_child),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.toolbar
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SearchField(
                        text = viewState.searchText,
                        label = stringResource(R.string.search_label),
//                        isError = viewState.isChildError,
//                        errorMessage = viewState.ChildErrorMessage,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp, bottom = 0.dp),
//                            .focusRequester(viewModel.listTextField[0])
//                            .onFocusChanged {
//                                if (it.isFocused)
//                                    viewState.indexFocus = 0
//                            }
                    ) { searchText ->
                        viewModel.obtainEvent(PayListEvent.Search(searchText = searchText))
                    }
                    Icon(
                        painter = painterResource(R.drawable.arrow_drop_up_24),
                        modifier = Modifier
                            .width(48.dp)
                            .clickable {
                                viewModel.obtainEvent(PayListEvent.onCollapse(true))
                            },
                        contentDescription = "",
                        tint = ClassJournalTheme.colors.controlColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Icon(
                        painter = if (viewState.isFilterData) painterResource(R.drawable.box_ckeck) else painterResource(
                            R.drawable.box_out
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 8.dp, end = 16.dp)
                            .clickable {
                                viewModel.obtainEvent(PayListEvent.isFilterDataClicked)
                            }
                    )
                    Text(
                        text = stringResource(R.string.filter_date),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.toolbar
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DatePickerFieldToModal(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                            .fillMaxWidth(0.5f),
                        if (viewState.beginDate.isNullOrEmpty())
                            LocalDate.now().minusYears(5)
                                .format(DateTimeFormatter.ofPattern(DATE_FORMAT_RU))
                        else {
                            viewState.beginDate
                        },
                        stringResource(R.string.begin_date)
                    ) {
                        viewModel.beginDateChange(it?.toDateStringRU().toString())
                    }
                    DatePickerFieldToModal(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                        //.fillMaxWidth(0.5f)
                        ,
                        if (viewState.endDate.isNullOrEmpty())
                            LocalDate.now().minusYears(5)
                                .format(DateTimeFormatter.ofPattern(DATE_FORMAT_RU))
                        else {
                            viewState.endDate
                        },
                        stringResource(R.string.end_date)
                    ) {
                        viewModel.endDateChange(it?.toDateStringRU().toString())
                    }
                }
            }
        }
    }
}
