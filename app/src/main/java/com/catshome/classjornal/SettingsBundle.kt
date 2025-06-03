package com.catshome.ClassJournal.com.catshome.classjornal

data class SettingsBundle(
    val isDarkMode: Boolean,
    val textSize: ClassJournalSize,
    val paddingSize: ClassJournalSize,
    val cornerStyle: ClassJournalCorners,
    val style: ClassJournalStyle
)
