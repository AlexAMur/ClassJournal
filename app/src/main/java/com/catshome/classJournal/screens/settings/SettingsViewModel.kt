package com.catshome.classJournal.screens.settings

import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): BaseViewModel<SettingsViewState, SettingsAction, SettingsEvent>
    (installState = SettingsViewState())
{
   override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.ClearAllQueries -> {}
            SettingsEvent.BackClicked -> viewAction = SettingsAction.NavigateBack
        }
    }


}