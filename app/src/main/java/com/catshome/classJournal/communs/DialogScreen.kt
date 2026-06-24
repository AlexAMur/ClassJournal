package com.catshome.classJournal.communs


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
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
    textContentColor: Color = ClassJournalTheme.colors.primaryText,
    containerColor: Color = ClassJournalTheme.colors.primaryBackground,
    confirmButtonColor: ButtonColors = ButtonColors(
        containerColor = ClassJournalTheme.colors.tintColor,
        contentColor = ClassJournalTheme.colors.primaryText,
        disabledContainerColor = ClassJournalTheme.colors.disableColor,
        disabledContentColor = ClassJournalTheme.colors.primaryText
    ),
    dismissButtonColor: ButtonColors = ButtonColors(
        containerColor = ClassJournalTheme.colors.tintColor,
        contentColor = ClassJournalTheme.colors.primaryText,
        disabledContainerColor = ClassJournalTheme.colors.disableColor,
        disabledContentColor = ClassJournalTheme.colors.primaryText
    )


) {

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = containerColor,
        title = {
            Text(
                text = title,
                color = textContentColor
            )
                },
        text = { Text(text) },
        textContentColor = textContentColor,
        confirmButton = {
            if (onConfirm != null) {
                TextButton(
                    onClick = onConfirm,
                    colors = confirmButtonColor) {
                    Text(
                        text = confimText?:"",
//                        color = confirmButtonColor
                    ) }
            }
        },
        dismissButton = {

            TextButton(onClick = onDismiss,
                    colors = dismissButtonColor
            ) {
                Text(
                    text = dissmissText
                )

            }
        }
    )

}