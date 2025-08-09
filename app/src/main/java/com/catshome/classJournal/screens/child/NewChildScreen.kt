package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.navigate.DetailsChild


@Composable
fun NewChildScreen(
    idChild: DetailsChild, viewModel: NewChildViewModel = viewModel()
) {
    val outerNavigation = localNavHost.current
    var bottomPadding by remember { mutableStateOf(0.dp) }
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val openDialog = remember {
        mutableStateOf(false)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Dialog(
        onDismissRequest = {
            openDialog.value = false
            outerNavigation.popBackStack()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {


        Card(
            modifier = Modifier.fillMaxSize()
                , colors = CardDefaults.cardColors(
                containerColor = ClassJournalTheme.colors.primaryBackground,
                disabledContainerColor = ClassJournalTheme.colors.primaryBackground
            )
        ) {
                 Column(modifier = Modifier.imePadding()
                .verticalScroll(state = rememberScrollState()))
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {
                        openDialog.value = false
                        outerNavigation.popBackStack()
                        Log.e("CLJR", "CLick close!!!")

                    }) {
                        Icon(
                            Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                        )
                    }
                    Text(
                        stringResource(R.string.new_child_dialog_headline),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.caption
                    )
                    TextButton(onClick = {
                        outerNavigation.popBackStack()
                    }) {
                        Text(
                            stringResource(R.string.save_button),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }

                }

                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                TextField(
                    value = viewState.name,
                    label = stringResource(R.string.name_child),
                    supportingText = stringResource(R.string.name_child),
                    modifier = modifier,
                    onValueChange = {viewModel.nameChange(it) },
                )
                TextField(
                    value = viewState.surname,
                    label = stringResource(R.string.surname_child),
                    supportingText = stringResource(R.string.surname_child),
                    modifier = modifier,
                    onValueChange = { viewModel.surnameChange(it) },
                )
                TextField(
                    value = viewState.birthday,
                    label = stringResource(R.string.birthday_child),
                    supportingText = stringResource(R.string.birthday_child),
                    modifier = modifier,
                    onValueChange = {viewModel.birthdayChange(it) },
                )
                TextField(
                    value = viewState.phone,
                    label = stringResource(R.string.phone_child),
                    supportingText = stringResource(R.string.phone_child),
                    modifier = modifier,
                    keyboardOptions = KeyboardOptions.Default.merge(KeyboardOptions(keyboardType = KeyboardType.Phone)),
                    onValueChange = { phone -> viewModel.phoneChange(phone) },
                )
                TextField(
                    value = viewState.note,
                    label = stringResource(R.string.note_label),
                    supportingText = stringResource(R.string.note_label),
                    modifier = modifier,
                    onValueChange = { note -> viewModel.noteChange(note) },
                    minLines = 4
                )
                Spacer(Modifier.fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        val imeBottom =WindowInsets.ime.getBottom(LocalDensity.current)
                        val buttonBottom = coordinates.positionInWindow().y + coordinates.size.height
                        bottomPadding = if (buttonBottom > imeBottom && imeBottom !=0) {
                            (buttonBottom - imeBottom).dp
                        } else {
                            0.dp
                        }
                    }


                    .padding(bottom = bottomPadding)


                )

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
    minLines: Int = 1,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorState:  Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        isError = errorState,
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
        minLines = minLines
    )

}