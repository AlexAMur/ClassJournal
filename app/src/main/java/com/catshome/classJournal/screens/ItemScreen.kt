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

enum class ItemScreen {
    MainScreen,
    PayListScreen,
    VisitListScreen,
    GroupScreen,
    RateScreen,
    ReportScreen,
    NewChildScreen,
    NewPayScreen,
    NewVisitScreen,
    NewRateScreen,
    NewGroupScreen
}
