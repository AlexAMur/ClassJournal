package com.catshome.classJournal.communs


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun DialogScreen(
    title: String,
    text: String,
    onConfirm: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    confimText: String? = null,
    dissmissText: String  = "Отмена",
    textContentColor: Color = ClassJournalTheme.colors.primaryText

) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(text) },
        textContentColor = textContentColor,
        confirmButton = {
            if (onConfirm != null) {
                TextButton(onClick = onConfirm) { Text(confimText?:"") }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(dissmissText) }
        }
    )

}