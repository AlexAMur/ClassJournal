package com.catshome.classJournal.proto

import com.catshome.classJournal.ClassJournalStyle
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    var colorMode: ColorMode = ColorMode.Dark,
    var style: ClassJournalStyle = ClassJournalStyle.Blue
    )
enum class ColorMode {
    Light, Dark
}