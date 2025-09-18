package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.DatePickerFieldToModal
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.screens.group.ComposeAction


@Composable
fun NewChildScreen(
    idChild: DetailsChild, viewModel: NewChildViewModel = viewModel()

) {

    val viewState by viewModel.viewState().collectAsState()
    viewState.outerNavigation = localNavHost.current
    val viewAction by viewModel.viewActions().collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current



    ScreenContent(viewState, viewModel,
        onCancelClick = {
            Log.e("CLJR", "Cancel Click!!!!!!!!!!!!")
            viewModel.obtainEvent(NewChildEvent.CloseClicked)
                        },
        onSaveClick = {
        Log.e("CLJR", "Save Click!!!!!!!!!!!!")
        viewModel.obtainEvent(NewChildEvent.SaveClicked)
    })


    when(viewAction){
        ComposeAction.Success -> {
            keyboardController?.hide()
            viewState.outerNavigation?.popBackStack()
            viewModel.clearAction()
        }

        ComposeAction.CloseScreen -> {
            keyboardController?.hide()
            viewState.outerNavigation?.popBackStack()
            viewModel.clearAction()
        }

        NewChildAction.CloseClicked -> {
            viewState.outerNavigation?.popBackStack()
        }
        is NewChildAction.SaveChild -> { }
        NewChildAction.SaveClicked -> {
            viewModel.obtainEvent(NewChildEvent.CloseClicked)
        }
        null -> {}
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenContent(
   viewState: NewChildState,
    viewModel: NewChildViewModel,
   onSaveClick: ()->Unit,
   onCancelClick: ()->Unit
) {

    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()


    val groups by viewModel.groups.collectAsState(null)


    val snackbarHostState = remember { SnackbarHostState() }
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
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[1])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 1
                        },

                    onValueChange = { viewModel.surnameChange(it) },
                )

                DatePickerFieldToModal(
                    modifier = modifier, viewState,
                    stringResource(R.string.birthday_child)
                )

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
                TextField(
                    value = viewState.child.note,
                    label = stringResource(R.string.note_label),
                    supportingText = stringResource(R.string.note_label),
                    modifier = modifier
                        .focusRequester(viewModel.listTextField[3])
                        .onFocusChanged {
                            if (it.isFocused)
                                viewState.indexFocus = 3
                        },

                    onValueChange = { note -> viewModel.noteChange(note) },
                    singleLine = false,
                    minLines = 4
                )


                val group = listOf("group1", "group2", "group3", "group4", "group5")

                LazyColumn(
                    Modifier
                        .height(250.dp)
                        .padding(16.dp)
                        .border(
                            1.dp,
                            color = ClassJournalTheme.colors.primaryText,
                            shape = ClassJournalTheme.shapes.cornersStyle
                        )

                        .fillMaxWidth()
                )
                {
                    groups?.let {
                        itemsIndexed(groups!!) { index, item ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    item.name,
                                    style = ClassJournalTheme.typography.body,
                                    color = ClassJournalTheme.colors.primaryText
                                )
                                Icon(
                                    Icons.Default.Check,
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

@Composable
fun TextField(
    value: String,
    label: String,
    supportingText: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    //maxLines: Int =1,
    minLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorState: Boolean = false,
    readOnly: Boolean = false

) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        isError = errorState,
        readOnly = readOnly,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = ClassJournalTheme.colors.primaryText,
            unfocusedTextColor = ClassJournalTheme.colors.primaryText,
            focusedBorderColor = ClassJournalTheme.colors.tintColor,
            focusedSupportingTextColor = ClassJournalTheme.colors.tintColor,
            unfocusedSupportingTextColor = ClassJournalTheme.colors.primaryText,
            focusedLabelColor = ClassJournalTheme.colors.tintColor,
            unfocusedLabelColor = ClassJournalTheme.colors.primaryText,
            errorLabelColor = ClassJournalTheme.colors.errorColor,
            errorBorderColor = ClassJournalTheme.colors.errorColor,
            errorSupportingTextColor = ClassJournalTheme.colors.errorColor

        ),
        label = { Text(label) },
        supportingText = { Text(text = supportingText) },
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        singleLine = singleLine,
        minLines = minLines,
        trailingIcon = trailingIcon
    )

}