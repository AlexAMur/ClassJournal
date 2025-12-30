package com.catshome.classJournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.catshome.classJournal.screens.PayList.NewPayEvent
import com.catshome.classJournal.screens.Visit.NewVisitViewModel
import androidx.compose.runtime.getValue
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitScreen(
    viewModel: NewVisitViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
    Card {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (WindowInsets.Companion.isImeVisible) 0.dp
                    else bottomPadding
                )
                .imePadding()
                .verticalScroll(state = rememberScrollState())
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            SearchField(
                text = viewState.searchText,
                label = stringResource(R.string.search_label),
                isError = viewState.isChildError,
                errorMessage = viewState.ChildErrorMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
                    .focusRequester(viewModel.listTextField[0])
                    .onFocusChanged {
                        if (it.isFocused)
                            viewState.indexFocus = 0
                    },
            ) { searchText ->
                viewModel.obtainEvent(NewVisitEvent.Search(searchText = searchText))
            }
            viewState.listChild?.let { listChild ->
                if (listChild.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .offset(y = (-19).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ClassJournalTheme.colors.secondaryBackground,
                            contentColor = ClassJournalTheme.colors.primaryText
                        ),
                        border = BorderStroke(2.dp, color = ClassJournalTheme.colors.tintColor),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 8.dp)
                                .heightIn(min = 0.dp, max = 300.dp)
                        ) {
                            itemsIndexed(listChild) { index, child ->
                                ItemChildInSearch(
                                    name = child.name,
                                    surname = child.surname,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp,
                                            top = 4.dp,
                                            end = 16.dp,
                                            bottom = 8.dp
                                        ),
                                    style = ClassJournalTheme.typography.body,
                                    contentColor = ClassJournalTheme.colors.tintColor,
                                    onClicked = {

                                    },
                                )
                            }
                        }
                    }
                }
            }

        }
    }

}