package com.catshome.classJournal.screens.group

import com.catshome.classJournal.domain.Group.Models.Group


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
    var uidDelete: Long  = -1,
    var isDelete: Boolean =false,
    val listGroup: List<Group> =emptyList()
)
