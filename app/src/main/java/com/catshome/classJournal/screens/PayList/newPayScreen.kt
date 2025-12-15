package com.catshome.classJournal.screens.PayList

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.context
import com.catshome.classJournal.localNavHost
import com.catshome.classJournal.navigate.DetailsPayList
import com.catshome.classJournal.screens.ItemScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun newPayScreen(
    viewModel: NewPayViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val outerNavigation = localNavHost.current
    val viewAction by viewModel.viewActions().collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()

    val sbHostState = remember { SnackbarHostState() }
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
                        actionLabel = viewState.snackbarAction
                            ?: context.getString(R.string.bottom_yes),
                        sbHostState,
                        onDismissed = viewState.onDismissed ?: {},
                        onActionPerformed = viewState.onAction ?: {}
                    )
                }
            }
        }
    ) {
        PayScreenContent(
            viewState,
            viewModel,
            onCancelClick = {
                viewModel.obtainEvent(NewPayEvent.CancelClicked)
            },
            onSaveClick = {
                keyboardController?.hide()
                viewModel.obtainEvent(NewPayEvent.SaveClicked)
            }
        )
        when (viewAction) {
            NewPayAction.Successful -> {
                keyboardController?.hide()
                Log.e("CLJR", "NAvigation")
                viewModel.clearAction()
                outerNavigation?.navigate(
                    DetailsPayList(
                        isShowSnackBar = true,
                        Message = stringResource(R.string.save_successful)
                    )
                ) {
                    popUpTo(ItemScreen.PayListScreen.name) {
                        inclusive = true
                    }
                }
                //outerNavigation.popBackStack()
            }

            NewPayAction.CloseScreen -> {
                keyboardController?.hide()
                viewModel.clearAction()
                outerNavigation.popBackStack()
            }

            null -> {}
        }
    }
}