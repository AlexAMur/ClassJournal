package com.catshome.classJournal.communs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.catshome.classJournal.ClassJournalTheme
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SearchField(label: String ="",
                text: String,
                modifier: Modifier = Modifier,
                onSearch:(String)->Unit){


    TextField(
        value = text,
        label = label,
        modifier = modifier,
        errorState = false,
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
        supportingText = null,

    )
}