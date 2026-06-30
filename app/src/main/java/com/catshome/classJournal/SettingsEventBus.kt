package com.catshome.classJournal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.catshome.classJournal.proto.AppSettings
import com.catshome.classJournal.proto.ColorMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsEventBus() {


    private val _currentSettings: MutableStateFlow<SettingsBundle> = MutableStateFlow(
        SettingsBundle(
            isDarkMode = appSetting?.colorMode?.name == ColorMode.Dark.name,
            cornerStyle = ClassJournalCorners.Rounded,
            style = ClassJournalStyle.Blue,
            textSize = ClassJournalSize.Medium,
            paddingSize = ClassJournalSize.Medium,
            innerPadding = PaddingValues(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
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
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.updateData {
                it.copy(style = style)
            }
        }
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