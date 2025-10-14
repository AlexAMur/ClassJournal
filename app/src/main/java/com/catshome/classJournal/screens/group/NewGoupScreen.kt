package com.catshome.classJournal.screens.group


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.screens.viewModels.NewGroupViewModel

@Composable
fun NewGroupScreen(
    idGroup: String = "",
    viewModel: NewGroupViewModel = viewModel()
) {
    if (idGroup != "")
        viewModel.obtainEvent(NewGroupEvent.OpenGroup(idGroup))
    val outerNavigation = localNavHost.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    //  Установка фокуса после переворота
    LaunchedEffect(true) {
        if (viewState.isFocus)
            viewState.focusRequester.requestFocus()
    }
  //  var isErrorDisplay by rememberSaveable { mutableStateOf(false) }

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
                    .imePadding()
                    .padding(start = 24.dp, end = 24.dp)
                    .height(200.dp),
                shape = ClassJournalTheme.shapes.cornersStyle,
                colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { viewModel.obtainEvent(NewGroupEvent.ActionInvoked) }) {
                        Icon(
                            Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                        )
                    }
                    Text(
                        stringResource(R.string.new_group_dialog_headline),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.heading
                    )
                    TextButton(onClick = { viewModel.obtainEvent(NewGroupEvent.SaveClicked) }) {
                        Text(
                            stringResource(R.string.save_button),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }

                }
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier =Modifier
                            .fillMaxWidth()
                            .focusRequester(viewState.focusRequester)
                            .onFocusChanged{
                                if (it.isFocused)
                                viewState.isFocus = it.isFocused
                            }
                            .padding(16.dp),
                        value = viewState.nameGroup,
                        label = stringResource(R.string.name_group_label),
                        supportingText = if (viewState.isError) viewState.errorMessage
                        else stringResource(R.string.name_group_label)
                                    ,
                        errorState = viewState.isError,
                        onValueChange = {
                            viewModel.obtainEvent(NewGroupEvent.ChangeName(it))
                        }
                    )
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
        null -> {}
    }
}