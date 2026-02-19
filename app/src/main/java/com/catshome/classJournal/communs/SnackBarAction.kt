package com.catshome.classJournal.communs

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun SnackBarAction(message: String, actionLabel: String,
                           snackBarState: SnackbarHostState,
                           onDismissed:()->Unit,
                           onActionPerformed:()-> Unit ){
         when( snackBarState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
             withDismissAction = true,
            duration = SnackbarDuration.Short
        )){
             SnackbarResult.Dismissed -> onDismissed()
             SnackbarResult.ActionPerformed -> onActionPerformed()
         }

    }
