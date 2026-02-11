package com.catshome.classJournal.screens.Scheduler

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import kotlin.time.Clock

@Composable
fun ItemListScheduler(text: String, onClick: ()->Unit){
    val offsetAnime = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(offset) {
        offsetAnime.animateTo(offset)
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .offset(x = offsetAnime.value.dp)
            .background(ClassJournalTheme.colors.secondaryBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 8.dp),
            text = text,
            style = ClassJournalTheme.typography.body,

            )
        IconButton(onClick = onClick) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = ClassJournalTheme.colors.tintColor
            )
        }
    }
}