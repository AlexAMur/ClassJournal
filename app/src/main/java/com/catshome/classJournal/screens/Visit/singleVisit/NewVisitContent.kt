package com.catshome.classJournal.screens.Visit.singleVisit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitViewModel
import java.util.Date
import kotlin.time.Clock

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitContent(
    viewModel: NewVisitViewModel,
) {
    val viewState by viewModel.viewState().collectAsState()
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.
                    collectAsState().value.innerPadding.calculateBottomPadding()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(ClassJournalTheme.colors.primaryBackground)
    ) {
        SingleVisitContent(
            fio = viewState.searchText,
            price = viewState.priceScreen,
            onValueChange = { inputText ->
//                Log.e("CLJR", "OnValueChange!! $inputText  ${viewState.searchText.text}")
                viewState.searchText = inputText
                viewModel.obtainEvent(NewVisitEvent.Search(inputText))
            },
            isSearchError = viewState.isSearchError,
            isPriceError = viewState.isPriceError,
            date = Date(viewState.selectDate ?: Clock.System.now().toEpochMilliseconds()),
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
            isShowList = viewState.isSelectChild
        ) { child ->
            viewModel.obtainEvent(NewVisitEvent.SelectChild(child))
        }
    }
}