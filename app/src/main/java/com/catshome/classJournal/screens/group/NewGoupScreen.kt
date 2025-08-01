package com.catshome.classJournal.screens.group


import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.screens.viewModels.NewGroupViewModel

@Composable
fun NewGroupScreen(
    idGroup: Long,
    viewModel: NewGroupViewModel = viewModel()
) {
    val outerNavigation = localNavHost.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    var isErrorDisplay by remember { mutableStateOf(false) }

    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxSize(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .height(200.dp),
                shape = ClassJournalTheme.shapes.cornersStyle,
                colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground)
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = viewState.nameGroup,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = ClassJournalTheme.colors.primaryText,
                            unfocusedTextColor = ClassJournalTheme.colors.primaryText
                        ),
                        label = { Text(stringResource(R.string.name_group_label)) },
                        supportingText = {
                            Text(
                                text = stringResource(R.string.error_name_group),
                                color = if (isErrorDisplay) ClassJournalTheme.colors.errorColor
                                else ClassJournalTheme.colors.primaryText
                            )
                        },
                        onValueChange = {
                            if (isErrorDisplay)
                                isErrorDisplay = false
                            viewModel.obtainEvent(NewGroupEvent.ChangeName(it))
                        }
                    )

                    Button(
                        modifier = Modifier
                            .width(75.dp),
                        onClick = {
                            if (viewState.nameGroup.trim().isEmpty()) {
                                isErrorDisplay = true
                            } else {
                                viewModel.obtainEvent(NewGroupEvent.SaveClicked)
                            }

                        }) {
                        Text("OК")
                    }
                }
            }
        }
    }
    when (viewAction) {
        ComposeAction.Success -> {
            keyboardController?.hide()
            outerNavigation.popBackStack()
            viewModel.clearAction()
        }

        ComposeAction.CloseScreen -> {
            keyboardController?.hide()
            outerNavigation.popBackStack()
            viewModel.clearAction()
        }

//        ComposeAction.New -> {}
        null -> {}
    }
}