package com.catshome.ClassJournal.Screens.Group

import com.catshome.ClassJournal.domain.Group.Models.Group


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        class UpdateGroupClicked(val group: Group) : GroupEvent()
        class DeleteClicked(val uid: Int) : GroupEvent()
        //data object ActionInvoked : NewGroupEvent()
}