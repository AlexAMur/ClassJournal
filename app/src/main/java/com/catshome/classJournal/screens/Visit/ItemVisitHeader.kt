package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu

@Composable
fun ItemVisitHeader(header: String){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            ClassJournalTheme.colors.secondaryBackground
        )
    )
    {
        Text(
            modifier = Modifier.padding(8.dp),
            text =  header,
            color = ClassJournalTheme.colors.primaryText
        )
    }

}