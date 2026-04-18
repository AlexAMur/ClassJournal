package com.catshome.classJournal.communs

import androidx.collection.emptyLongSet
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun SearchField(
    label: String = "",
    text: TextFieldValue,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onClickCancel: ()->Unit = {},
    onSearch: (TextFieldValue) -> Unit
) {


    TextField(
        value = text,
        label = label,
        modifier = modifier,
        errorState = isError,
        onValueChange = {
            onSearch(it)
        },
        trailingIcon = {
            if (text.text.isEmpty()) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier,
                    tint = ClassJournalTheme.colors.controlColor
                )
            } else {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable{onClickCancel()},
                    tint = ClassJournalTheme.colors.controlColor
                )
            }

        },
        supportingText = errorMessage,

        )
}