package com.catshome.classJournal.communs

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun SnackBarAction(
    message: String,
    actionLabel: String?,
    snackBarState: SnackbarHostState,
    withDismissAction: Boolean =true,
    onDismissed: () -> Unit,
    onActionPerformed: () -> Unit,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    when (snackBarState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration
    )) {
        SnackbarResult.Dismissed -> {
            onDismissed()
        }
        SnackbarResult.ActionPerformed -> {
            onActionPerformed()
        }
    }
}
