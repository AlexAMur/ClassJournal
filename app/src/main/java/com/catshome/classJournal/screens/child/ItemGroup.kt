package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme

@Composable
fun ItemGroup(nameGroup: String, onClick: ()-> Unit) {
    Card(
        Modifier
            .padding(bottom = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .clickable{onClick()},
        shape = ClassJournalTheme.shapes.cornersStyle,
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor)
    ) {
        Text(
            nameGroup,
            Modifier.fillMaxWidth()
                .height(56.dp)
                .padding(start = 16.dp, top = 16.dp)
            ,
            color = ClassJournalTheme.colors.primaryText,
            style = ClassJournalTheme.typography.body

        )
    }
}