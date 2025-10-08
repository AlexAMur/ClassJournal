package com.catshome.classJournal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.catshome.classJournal.ClassJournalTheme


@Composable
fun ScreenNoItem(text: String, bottomPadding: Dp) {
    Surface(
        Modifier.Companion
            .fillMaxSize()
            .background(ClassJournalTheme.colors.primaryBackground),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.Companion.background(ClassJournalTheme.colors.primaryBackground)
        ) {
            Text(
                text = text,
                modifier = Modifier.Companion
                    .padding(bottom = bottomPadding)
                    .align(Alignment.Companion.CenterHorizontally),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.heading
            )
        }
    }
}