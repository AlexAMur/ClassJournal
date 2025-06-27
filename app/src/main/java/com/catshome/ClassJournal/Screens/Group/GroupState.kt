package com.catshome.ClassJournal.Screens.Group

import android.widget.Button
import com.catshome.ClassJournal.NewGroupScreen
import com.catshome.ClassJournal.domain.Group.Models.Group


data class NewGroupState (
    var uid : Long =0,
    var isDelete: Boolean =false,
    var nameGroup:String  = ""
){
    fun copy(group: Group): NewGroupState{
        return NewGroupState( uid =group.uid,
        isDelete =group.isDelete,
        nameGroup =group.name)
    }
}


data class GroupState(
    var indexButton: Int = -1,
    val listGroup: List<Group> =emptyList()
)
