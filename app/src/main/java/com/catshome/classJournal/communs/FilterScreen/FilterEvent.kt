package com.catshome.classJournal.communs.FilterScreen

import androidx.compose.material.TextFieldColors
import androidx.compose.ui.text.input.TextFieldValue
import com.catshome.classJournal.domain.Child.MiniChild

sealed class FilterEvent {
    data object Successful: FilterEvent()
    data object NewStatus: FilterEvent()
    data object BackClick: FilterEvent()
    data object ClearSearch: FilterEvent()
    data class SelectedIndex(val index: Int): FilterEvent()
    data class Init(val init: FilterSetting): FilterEvent()
    data class ChildSelected(val child: MiniChild): FilterEvent()
    data class Search(val value: TextFieldValue): FilterEvent()
    data class SelectSort(val value: String): FilterEvent()
}