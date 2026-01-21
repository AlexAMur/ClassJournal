package com.catshome.classJournal.screens.Scheduler

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.R
import com.catshome.classJournal.domain.Scheduler.Scheduler

@Composable
fun itemScheduler(day: String,scheduler:  List<Scheduler>?){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp, end = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
        shape = ClassJournalTheme.shapes.cornersStyle
    )

    {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column(Modifier.fillMaxWidth(0.75f)) {
                Text(
                    fio,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.body
                )

                Text(
                    stringResource(R.string.paymant_date) + " $date",
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.caption
                )

            }
            Text(
                "${payment} p.",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = ClassJournalTheme.colors.primaryText,
                style = ClassJournalTheme.typography.body
            )
        }
    }

}
