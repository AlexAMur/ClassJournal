package com.catshome.classjornal.Screens.Group

sealed class GroupEvent {

        data class ChangeName(val nameGroup: String) : GroupEvent()
        data class DeleteGroup(val isDelete: Boolean):GroupEvent()
        data object NextClicked : GroupEvent()
        data object ActionInvoked : GroupEvent()

}