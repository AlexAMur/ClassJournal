package com.catshome.classJournal.screens.child

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun ItemGroup(nameGroup: String, onClick:()-> Unit) {
    Card(
        Modifier
            .padding(bottom = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        shape = ClassJournalTheme.shapes.cornersStyle,
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor)
    ) {
        Text(
            nameGroup,
            Modifier.padding(16.dp),
            color = ClassJournalTheme.colors.primaryText,
            style = ClassJournalTheme.typography.body

        )
    }
}