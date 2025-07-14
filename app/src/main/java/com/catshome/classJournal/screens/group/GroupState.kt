package com.catshome.classJournal.screens.group

import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState
import androidx.wear.compose.material.RevealValue
import com.catshome.classJournal.domain.Group.Models.Group


data class NewGroupState (
    var uid : Long =0,
    var isDelete: Boolean =false,
    var nameGroup:String  = "",
    var isError: Boolean =false

){
    fun copy(group: Group): NewGroupState{
        return NewGroupState( uid =group.uid,
        isDelete =group.isDelete,
        nameGroup =group.name)
    }
}
data class GroupItem @OptIn(ExperimentalWearMaterialApi::class) constructor(
    var revealState: RevealState? = null,
    val group: Group
)

data class GroupState @OptIn(ExperimentalWearMaterialApi::class) constructor(
    var uidDelete: Long  = -1,
    var isDelete: Boolean =false,
    var swipeUid: Long = - 1,
   // var revealState: RevealState? = null,
    val listItems: List<GroupItem> =emptyList()
)

@OptIn(ExperimentalWearMaterialApi::class)
fun GroupState.mapGroup(group: List<Group>): List<GroupItem>{
    return  group.map { list-> GroupItem(null,list) }
}