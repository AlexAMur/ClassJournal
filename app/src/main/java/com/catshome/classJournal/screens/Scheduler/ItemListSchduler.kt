package com.catshome.classJournal.screens.Scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.Visit.NewVisitEvent

@Composable
fun ItemListScheduler(image: Painter, text: String, price: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.background(ClassJournalTheme.colors.primaryBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.background(ClassJournalTheme.colors.primaryBackground),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.fillMaxHeight(),
                painter = image,
                contentDescription = null,
                tint = ClassJournalTheme.colors.tintColor
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(ClassJournalTheme.colors.primaryBackground)
                .padding(16.dp),
            text = text,
            style = ClassJournalTheme.typography.body,

            )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .background(ClassJournalTheme.colors.primaryBackground)
                .padding(16.dp),
            text = price,
            style = ClassJournalTheme.typography.body,

            )

    }
}