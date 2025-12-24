package com.catshome.classJournal.communs.FilterScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.DatePickerFieldToModal
import com.catshome.classJournal.communs.GroupButton
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.domain.communs.toDateStringRU
import com.catshome.classJournal.navigate.OptionFilterPaysList
import com.catshome.classJournal.screens.ItemScreen
import java.time.format.DateTimeParseException

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun FilterScreen(navController: NavController, setting: FilterSetting) {
    val viewModel by (LocalActivity.current as ComponentActivity).viewModels<FilterViewModel>()
   LaunchedEffect(Unit) {
       viewModel.obtainEvent(FilterEvent.Init(setting))
   }
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val columnVerticalScrollSate = rememberScrollState()
    val listState = rememberLazyListState()
    val localDensity = LocalDensity.current
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState().value
            .innerPadding.calculateBottomPadding()
    var screenHigh = LocalConfiguration.current.screenHeightDp
    var sizeList = 200f
    var bottomGroup = 0
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(color = ClassJournalTheme.colors.secondaryBackground) {
        Column(
            Modifier
                .padding(bottom = LocalSettingsEventBus.current.currentSettings.collectAsState()
                                .value.innerPadding.calculateBottomPadding().value.dp)
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(columnVerticalScrollSate)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {
                        viewModel.obtainEvent(FilterEvent.BackClick)
                    },
                    shape = ClassJournalTheme.shapes.cornersStyle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ClassJournalTheme.colors.tintColor,
                        contentColor = ClassJournalTheme.colors.primaryText
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = viewState.statusText,
                    color = ClassJournalTheme.colors.primaryText
                )
            }
            SearchField(
                text = viewState.searchText,
                label = stringResource(R.string.search_label),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
                    .onGloballyPositioned { coordinate ->
                        sizeList =
                            (screenHigh - (bottomPadding.value + coordinate.positionOnScreen().y +
                                    bottomGroup + coordinate.size.height) / localDensity.density).toFloat()
                    },
                onClickClose = {
                    viewModel.obtainEvent(FilterEvent.ClearSearch)
                }
            ) { searchText ->
                viewModel.obtainEvent(FilterEvent.Search(value = searchText))
            }
            AnimatedVisibility(viewState.isShowList) {
                viewState.searchList?.let { listChild ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .offset(y = (-18).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ClassJournalTheme.colors.secondaryBackground,
                            contentColor = ClassJournalTheme.colors.secondaryText
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = ClassJournalTheme.shapes.padding,
                            bottomEnd = ClassJournalTheme.shapes.padding
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = ClassJournalTheme.colors.tintColor
                        )
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .heightIn(
                                    min = 56.dp,
                                    max = sizeList.dp - 16.dp
                                ),
                            state = listState
                        ) {
                            items(listChild.size) { index ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp)
                                        .height(40.dp)
                                        .background(ClassJournalTheme.colors.controlColor)
                                        .clickable {
                                            if (listChild[index].uid.isNotEmpty()) {
                                                viewModel.obtainEvent(
                                                    FilterEvent.ChildSelected(
                                                        listChild[index]
                                                    )
                                                )
                                                keyboardController?.hide()
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 16.dp),
                                        color = ClassJournalTheme.colors.primaryText,
                                        text = listChild[index].fio,
                                        style = ClassJournalTheme.typography.caption,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        bottomGroup = it.size.height
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GroupButton(viewState.optionList, viewState.selectedOption) { index ->
                    viewModel.obtainEvent(FilterEvent.SelectedIndex(index))
                }
                AnimatedVisibility(viewState.isShowPeriod) {
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
                            value = viewState.beginDate,
                            label = stringResource(R.string.begin_date)
                        ) {
                            viewModel.beginDateChange("${it?.toDateStringRU()} 00:00:00")
                        }
                        DatePickerFieldToModal(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                            value = viewState.endDate,
                            label = stringResource(R.string.end_date)
                        ) {
                            viewModel.endDateChange("${it?.toDateStringRU()} 23:59:59")
                        }
                    }
                }

                radioOptionSorting(viewState.textSorting, viewState.sortList)
                {text->
                    viewModel.obtainEvent(FilterEvent.SelectSort(text))
                }
                Button(
                    modifier = Modifier,
                    shape = ClassJournalTheme.shapes.cornersStyle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ClassJournalTheme.colors.tintColor,
                        contentColor = ClassJournalTheme.colors.primaryText
                    ),
                    onClick = {
                        viewModel.obtainEvent(FilterEvent.Successful)
                    }) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
    }
    when (viewAction) {
        FilterAction.CloseScreen -> {
            keyboardController?.hide()
            navController.navigate(ItemScreen.PayListScreen.name){
                popUpTo(ItemScreen.PayListScreen.name){
                    inclusive =true
                }
            }
            viewModel.clearAction()
        }

        FilterAction.Successful -> {
            keyboardController?.hide()
            viewModel.clearAction()

            try {
                navController.navigate(
                    OptionFilterPaysList(
                        childId = viewState.selectChild?.uid,
                        childFIO = viewState.selectChild?.fio,
                        selectOption = viewState.selectedOption,
                        sort = viewState.sortValue,
                        beginDate = viewState.beginDate,
                        endDate = viewState.endDate
                    )
                ) {
                    popUpTo(ItemScreen.PayListScreen.name) {
                        inclusive = true
                    }
                }
            } catch (e: DateTimeParseException) {
                Log.e("CLJR", "SuccessfulAction on filter screen ${e.message}")
            }
        }

        null -> {}
    }
}