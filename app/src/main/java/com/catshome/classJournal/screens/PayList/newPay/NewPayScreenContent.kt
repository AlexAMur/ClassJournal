package com.catshome.classJournal.screens.PayList.newPay

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.communs.DatePickerFieldToModal
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.screens.PayList.ItemChildInSearch

//import com.catshome.classJournal.domain.communs.toDateStringRU

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PayScreenContent(
    viewState: NewPayState,
    viewModel: NewPayViewModel,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
//    val viewState by viewModel.viewState().collectAsState()
//  Установка фокуса после переворота
    LaunchedEffect(true) {
        if (viewState.indexFocus > -1)
            viewModel.listTextField[viewState.indexFocus].requestFocus()
    }
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = ClassJournalTheme.colors.primaryBackground,
            disabledContainerColor = ClassJournalTheme.colors.primaryBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ClassJournalTheme.colors.primaryBackground)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onCancelClick) {
                    Icon(
                        Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                    )
                }
                Text(
                    stringResource(R.string.new_pay_dialog_headline),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.caption
                )
                TextButton(onClick = onSaveClick) {
                    Text(
                        stringResource(R.string.save_button),
                        color = ClassJournalTheme.colors.tintColor
                    )
                }
            }
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
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp),
                    color = ClassJournalTheme.colors.disableContentColor
                )
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                SearchField(
                    text = viewState.searchText.text,
                    label = stringResource(R.string.search_label),
                    isError = viewState.isChildError,
                    errorMessage = viewState.childErrorMessage,
                    onClickCancel = {
                        viewModel.obtainEvent(NewPayEvent.ClearClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
                        .focusRequester(viewModel.listTextField[0])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 0
                        })

                { searchText ->
                    viewModel.obtainEvent(NewPayEvent.Search(searchText = searchText.text))
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
                                        fio = child.fio,
                                        //surname = child.surname,
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
                                            if (child.uid.isNotEmpty())
                                                viewModel.obtainEvent(
                                                    NewPayEvent.SelectedChild(
                                                        child
                                                    )
                                                )
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
                Column( Modifier
                    .background(ClassJournalTheme.colors.primaryBackground)
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = ClassJournalTheme.colors.disableContentColor
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, bottom = 4.dp)

                    ) {
                        Text(
                            text = "${stringResource(R.string.current_saldo)}",
                            modifier = Modifier,
                            style = ClassJournalTheme.typography.body,
                            color = ClassJournalTheme.colors.primaryText
                        )
                        viewState.selectChild?.saldo?.let { saldo ->
                            Text(
                                text = "${saldo}",
                                modifier = Modifier
                                    .padding(start = 16.dp),
                                style = ClassJournalTheme.typography.body,
                                color = if (saldo >= 0) ClassJournalTheme.colors.primaryText
                                else
                                    ClassJournalTheme.colors.errorColor
                            )
                            Text(
                                text = "р.",
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                style = ClassJournalTheme.typography.body,
                                color = ClassJournalTheme.colors.primaryText
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(0.75f)
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        color = ClassJournalTheme.colors.disableContentColor
                    )
                }

                DatePickerFieldToModal(
                    modifier = if (viewState.isChildError) modifier.padding(top = 16.dp) else modifier,
                    viewState.pay.datePay.toLocalDateTimeRu(),
//                    ifEmpty {
//                        LocalDateTime.now()
//                            .format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_RU))
//                    },
                    stringResource(R.string.date_paymant)
                ) {
                    it?.let {
                        viewModel.datePayChange(it.toLocalDateTimeRuString(formatDate = FormatDate.Date))
                    }
                }

                TextField(
                    value = viewState.payment,
                    label = stringResource(R.string.paymant),
                    supportingText = if (viewState.isPayError) viewState.payError else stringResource(
                        R.string.paymant
                    ),
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[2])
                        .onFocusChanged {
                            if (it.isFocused) {
                                viewState.indexFocus = 2
                                viewModel.obtainEvent(NewPayEvent.onFocusePrice)

                            }
                        },
                    errorState = viewState.isPayError,
                    keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Number)),
                    onValueChange = { newPayment -> viewModel.paymentChange(newPayment.text) },
                )
            }
        }
    }
}