package com.catshome.classJournal.screens.child

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme


@Composable
fun itemChild(name: String, surname: String, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .background(ClassJournalTheme.colors.primaryBackground)

            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
        shape = ClassJournalTheme.shapes.cornersStyle
    )

    {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
           // Column(Modifier.fillMaxWidth(0.75f)) {
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                        //.//fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.toolbar
                )

                Text(
                    text = surname,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.toolbar
                )

            //}

        }
    }
}

