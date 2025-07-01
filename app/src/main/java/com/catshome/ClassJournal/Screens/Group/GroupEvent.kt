package com.catshome.ClassJournal.Screens.Group

import com.catshome.ClassJournal.domain.Group.Models.Group


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        data object ReloadScreen : GroupEvent()
        class UpdateGroupClicked(val group: Group) : GroupEvent()
        class DeleteClicked(val uid: Long) : GroupEvent()
        class UndoDeleteClicked(val uid: Long) : GroupEvent()
        //data object ActionInvoked : NewGroupEvent()
}