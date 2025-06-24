package com.catshome.ClassJournal.Screens.Group

import com.catshome.ClassJournal.domain.Group.Models.Group


data class NewGroupState (
    val uid : Long =0,
    val isDelete: Boolean =false,
    val nameGroup:String  = ""
)
data class GroupState(
    val listGroup: List<Group> =emptyList()
)