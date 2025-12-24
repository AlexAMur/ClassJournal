package com.catshome.classJournal.screens.child

import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.DatePickerFieldToModal
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.communs.DATETIME_FORMAT_RU
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateStringRU
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.navigate.DetailsChild
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
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT_RU))
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



