package com.catshome.classJournal.communs

import androidx.collection.emptyLongSet
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun SearchField(
    label: String = "",
    text: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onClickCancel: ()->Unit = {},
    onSearch: (String) -> Unit
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
            if (text.length == 0) {
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