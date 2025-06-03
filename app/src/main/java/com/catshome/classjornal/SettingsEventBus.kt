package com.catshome.ClassJournal.com.catshome.classjornal

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsEventBus {

    private val _currentSettings: MutableStateFlow<SettingsBundle> = MutableStateFlow(
        SettingsBundle(
            isDarkMode = true,
            cornerStyle = ClassJournalCorners.Rounded,
            style = ClassJournalStyle.Orange,
            textSize = ClassJournalSize.Medium,
            paddingSize = ClassJournalSize.Medium
        )
    )
    val currentSettings: StateFlow<SettingsBundle> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        println("New value $isDarkMode")
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }

    fun updateCornerStyle(corners: ClassJournalCorners) {
        _currentSettings.value = _currentSettings.value.copy(cornerStyle = corners)
    }

    fun updateStyle(style: ClassJournalStyle) {
        _currentSettings.value = _currentSettings.value.copy(style = style)
    }

    fun updateTextSize(textSize: ClassJournalSize) {
        _currentSettings.value = _currentSettings.value.copy(textSize = textSize)
    }

    fun updatePaddingSize(paddingSize: ClassJournalSize) {
        _currentSettings.value = _currentSettings.value.copy(paddingSize = paddingSize)
    }
}

internal val LocalSettingsEventBus = staticCompositionLocalOf {
    SettingsEventBus()
}