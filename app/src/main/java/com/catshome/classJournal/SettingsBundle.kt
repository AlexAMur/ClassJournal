package com.catshome.classJournal

import androidx.compose.foundation.layout.PaddingValues

data class SettingsBundle(
    val isDarkMode: Boolean,
    val textSize: ClassJournalSize,
    val paddingSize: ClassJournalSize,
    val cornerStyle: ClassJournalCorners,
    val style: ClassJournalStyle,
    var innerPadding:  PaddingValues
)
