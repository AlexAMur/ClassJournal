package com.catshome.classJournal.communs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun TextField(
    value: String,
    label: String,
    supportingText: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    minLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorState: Boolean = false,
    readOnly: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = ClassJournalTheme.colors.primaryText,
        unfocusedTextColor = ClassJournalTheme.colors.primaryText,
        focusedBorderColor = ClassJournalTheme.colors.tintColor,
        focusedSupportingTextColor = ClassJournalTheme.colors.tintColor,
        unfocusedSupportingTextColor = ClassJournalTheme.colors.primaryText,
        focusedLabelColor = ClassJournalTheme.colors.tintColor,
        unfocusedLabelColor = ClassJournalTheme.colors.primaryText,
        errorLabelColor = ClassJournalTheme.colors.errorColor,
        errorBorderColor = ClassJournalTheme.colors.errorColor,
        errorSupportingTextColor = ClassJournalTheme.colors.errorColor,
        errorTextColor = ClassJournalTheme.colors.errorColor,
        cursorColor = ClassJournalTheme.colors.tintColor,
    errorCursorColor = ClassJournalTheme.colors.errorColor,
    )

) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        isError = errorState,
        readOnly = readOnly,
        colors = colors,
        label = { Text(label) },
        supportingText = { Text(text = supportingText) },
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        singleLine = singleLine,
        minLines = minLines,
        trailingIcon = trailingIcon
    )

}