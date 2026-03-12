package com.catshome.classJournal.screens.child

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.communs.DATETIME_FORMAT_RU
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.navigate.DetailsChild
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.group.ComposeAction
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewChildScreen(
    idChild: DetailsChild = DetailsChild(""), viewModel: NewChildViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    viewState.outerNavigation = localNavHost.current
    val viewAction by viewModel.viewActions().collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

    val sbHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        if (idChild.childID != "")
            viewModel.obtainEvent(NewChildEvent.OpenChild(idChild.childID))
        else {
            viewState.child.birthday = LocalDate.now().minusYears(5)
                .format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_RU))
            viewModel.obtainEvent(NewChildEvent.newChild)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = bottomPadding),
                hostState = sbHostState
            )
            LaunchedEffect(viewState.isSnackbarShow) {
                if (viewState.isSnackbarShow) {
                    keyboardController?.hide()
                    SnackBarAction(
                        message = viewState.errorMessage,
                        actionLabel = viewState.snackbarAction?:context.getString(R.string.bottom_yes),
                        sbHostState,
                        onDismissed = viewState.onDismissed ?: {},
                        onActionPerformed = viewState.onAction ?: {}

                    )
                }
            }
        }
    ) {
        ScreenContent(
            viewState,
            viewModel,
            onCancelClick = {
                viewModel.obtainEvent(NewChildEvent.CloseClicked)
            },
            onSaveClick = {
                keyboardController?.hide()
                viewModel.obtainEvent(NewChildEvent.SaveClicked)
            }
        )

        when (viewAction) {
            ComposeAction.Success -> {
                keyboardController?.hide()
                viewState.outerNavigation?.popBackStack()
                viewModel.clearAction()
            }

            ComposeAction.CloseScreen -> {
                keyboardController?.hide()
                //viewState.outerNavigation?.popBackStack()
                viewModel.clearAction()
            }

            NewChildAction.CloseClicked -> {
                viewState.outerNavigation?.popBackStack()
            }

            is NewChildAction.SaveChild -> {}
            NewChildAction.SaveClicked -> {
                viewModel.obtainEvent(NewChildEvent.CloseClicked)
            }

            is NewChildAction.ReloadScreen -> {
//            ScreenContent(viewState,viewAction, viewModel,
//                onCancelClick = {
//                    viewModel.obtainEvent(NewChildEvent.CloseClicked)
//                },
//                onSaveClick = {
//                    viewModel.obtainEvent(NewChildEvent.SaveClicked)
//                })
            }

            null -> {}
        }
    }
}



