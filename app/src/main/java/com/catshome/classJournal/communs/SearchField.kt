package com.catshome.classJournal.communs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun SearchField(label: String = "",
                text: String,
                isError: Boolean = false,
                errorMessage: String? = null,
                modifier: Modifier = Modifier,
                onSearch:(String)->Unit){


    TextField(
        value = text,
        label = label,
        modifier = modifier,
        errorState = isError,
        onValueChange = {
            onSearch(it)
        },
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier,
                tint = ClassJournalTheme.colors.controlColor
            )
        },
        supportingText = errorMessage,

    )
}