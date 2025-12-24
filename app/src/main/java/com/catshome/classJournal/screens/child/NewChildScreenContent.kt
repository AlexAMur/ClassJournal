package com.catshome.classJournal.screens.child

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.DatePickerFieldToModal
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateStringRU
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenContent(
    viewState: NewChildState,
    viewModel: NewChildViewModel,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()
    //val viewState by viewModel.viewState().collectAsState()
    var groups = viewState.listScreenChildGroup

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
                    stringResource(R.string.new_child_dialog_headline),
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
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                TextField(
                    value = viewState.child.name,
                    label = stringResource(R.string.name_child),
                    supportingText = stringResource(R.string.name_child),
                    errorState = viewState.isNameError,
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[0])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 0
                        },
                    onValueChange = { viewModel.nameChange(it) },
                )
                TextField(
                    value = viewState.child.surname,
                    label = stringResource(R.string.surname_child),
                    supportingText = stringResource(R.string.surname_child),
                    errorState = viewState.isSurnameError,
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[1])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 1
                        },

                    onValueChange = { viewModel.surnameChange(it) },
                )

                DatePickerFieldToModal(
                    modifier = modifier,
                    if (viewState.child.birthday.isNullOrEmpty())
                        LocalDate.now().minusYears(5)
                            .format(DateTimeFormatter.ofPattern(DATE_FORMAT_RU))
                    else {

                        viewState.child.birthday
                    },

                    stringResource(R.string.birthday_child)
                ) {
                    viewModel.birthdayChange(it?.toDateStringRU().toString())
                }

                TextField(
                    value = viewState.child.phone,
                    label = stringResource(R.string.phone_child),
                    supportingText = stringResource(R.string.phone_child),
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[2])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 2
                        },

                    keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Phone)),
                    onValueChange = { phone -> viewModel.phoneChange(phone) },
                )
                if(viewState.isNewChild) {
                    TextField(
                        value = viewState.startSaldo.toString(),
                        label = stringResource(R.string.saldo_child),
                        supportingText = if (viewState.isSaldoError)stringResource(R.string.error_invalid_value)
                        else stringResource(R.string.saldo_child),
                        errorState = viewState.isSaldoError,
                        modifier = modifier
                            .focusRequester(viewModel.listTextField[3])
                            .onFocusChanged {
                                if (it.isFocused)
                                    viewState.indexFocus = 3
                            },

                        keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Phone)),
                        onValueChange = { saldo -> viewModel.saldoChange(saldo) },
                    )
                }


                TextField(
                    value = viewState.child.note,
                    label = stringResource(R.string.note_label),
                    supportingText = stringResource(R.string.note_label),
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[4])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 4
                        },

                    onValueChange = { note -> viewModel.noteChange(note) },
                    singleLine = false,
                    minLines = 4
                )
// рисуем список групп с отметками
                if (groups?.isNotEmpty() == true) {
                    LazyColumn(
                        Modifier
                            .height(250.dp)
                            .padding(16.dp)
                            .border(
                                1.dp,
                                color = ClassJournalTheme.colors.primaryText,
                                shape = ClassJournalTheme.shapes.cornersStyle
                            )
                            .fillMaxWidth(),
                        state = rememberLazyListState()
                    )
                    {
                        itemsIndexed(groups) { index, item ->

                            Card(
                                shape = ClassJournalTheme.shapes.cornersStyle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor),
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.obtainEvent(NewChildEvent.SelectGroup(item.group?.uid.toString()))
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 8.dp,
                                        bottom = 8.dp,
                                        end = 16.dp
                                    )
                                    Text(
                                        item.group?.name ?: "null",
                                        modifier = modifier,
                                        style = ClassJournalTheme.typography.body,
                                        color = ClassJournalTheme.colors.primaryText
                                    )

                                    if (item.isChecked) {
                                        Icon(
                                            painter = painterResource(R.drawable.box_ckeck),
                                            modifier = modifier,
                                            contentDescription = "",
                                            tint = ClassJournalTheme.colors.primaryText
                                        )
                                    } else {

                                        Icon(
                                            painter = painterResource(R.drawable.box_out),
                                            modifier = modifier,
                                            contentDescription = "",
                                            tint = ClassJournalTheme.colors.primaryText
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }

        }
    }
}