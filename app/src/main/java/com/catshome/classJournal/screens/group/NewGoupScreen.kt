package com.catshome.classJournal.screens.group


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.R
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.screens.viewModels.NewGroupViewModel


@Composable
fun NewGroupScreen (idGroup: Long,
    viewModel: NewGroupViewModel = viewModel()
     ) {
     viewModel.obtainEvent(NewGroupEvent.OpenGroup(idGroup))

    val outerNavigation= localNavHost.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    Surface(
        Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxSize(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Card(
            shape = ClassJournalTheme.shapes.cornersStyle
        ) {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    value = viewState.nameGroup,
                    supportingText = {
                        if (viewState.isError) {
                            Text(
                                stringResource(R.string.error_name_group),
                                color = ClassJournalTheme.colors.errorColor
                            )
                        } else {
                            Text(
                                stringResource(R.string.name_group_label),
                                color = ClassJournalTheme.colors.primaryText
                            )
                        }

                    },
                    onValueChange = { viewModel.obtainEvent(NewGroupEvent.ChangeName(it)) }
                )

                Button(onClick = { viewModel.obtainEvent(NewGroupEvent.SaveClicked) }) {
                    Text("OК")
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
        ComposeAction.New -> {}
        null -> {}
    }
}