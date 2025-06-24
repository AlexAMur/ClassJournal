package com.catshome.ClassJournal.Screens.Group

sealed class NewGroupEvent {
        data class ChangeName(val nameGroup: String) : NewGroupEvent()
        data class DeleteGroup(val isDelete: Boolean): NewGroupEvent()
        data object NextClicked : NewGroupEvent()
        data object SaveClicked : NewGroupEvent()
        data object ActionInvoked : NewGroupEvent()
}

