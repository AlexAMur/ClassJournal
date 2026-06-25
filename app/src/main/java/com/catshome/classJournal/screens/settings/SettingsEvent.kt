package com.catshome.classJournal.screens.settings

sealed class SettingsEvent {
    data object BackClicked: SettingsEvent()
    data object ClearAllQueries : SettingsEvent()
}