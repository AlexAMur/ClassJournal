package com.catshome.classJournal.screens.Scheduler.newScheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.screens.Scheduler.NewSchedulerViewModel


@Composable
fun NewSchedulerScreen(
    navController: NavController,
    viewModel: NewSchedulerViewModel = viewModel()
) {
    Card(
        modifier =Modifier
            .fillMaxSize()

            .background(ClassJournalTheme.colors.tintColor),


        shape = ClassJournalTheme.shapes.cornersStyle,
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.tintColor),

    ) {
        Column(
            Modifier
                .background(ClassJournalTheme.colors.primaryBackground)
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text("fhdsakjlfdsal", color = ClassJournalTheme.colors.primaryText)
            Text("fhdsak", color = ClassJournalTheme.colors.primaryText)

        }

    }
}