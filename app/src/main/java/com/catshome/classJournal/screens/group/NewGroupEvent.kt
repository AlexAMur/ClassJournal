package com.catshome.classJournal.screens.group

import com.catshome.classJournal.communs.ErrorClassJournal

sealed class NewGroupEvent {
        data class ChangeName(val nameGroup: String) : NewGroupEvent()
        data class OpenGroup(val id: Long) : NewGroupEvent()
        data class DeleteGroup(val isDelete: Boolean): NewGroupEvent()
        class ErrorGroup(val error: ErrorClassJournal) : NewGroupEvent()
        data object NextClicked : NewGroupEvent()
        data object SaveClicked : NewGroupEvent()
        data object ActionInvoked : NewGroupEvent()
}

