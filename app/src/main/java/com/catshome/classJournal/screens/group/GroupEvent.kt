package com.catshome.classJournal.screens.group

import com.catshome.classJournal.domain.Group.Models.Group


sealed class GroupEvent {
        data object NewClicked : GroupEvent()
        data object ReloadScreen : GroupEvent()
   //     data object CancelDelete : GroupEvent()
    //    class RequestDelete(val uid: Long) : GroupEvent()

   //     class UpdateGroupClicked(val group: Group) : GroupEvent()
        class DeleteClicked(val uid: Long) : GroupEvent()
        class UndoDeleteClicked(val uid: Long) : GroupEvent()
        //data object ActionInvoked : NewGroupEvent()
}