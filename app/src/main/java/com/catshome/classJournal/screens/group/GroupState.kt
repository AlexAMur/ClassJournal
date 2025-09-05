package com.catshome.classJournal.screens.group

import androidx.compose.foundation.gestures.Orientation
import androidx.wear.compose.foundation.SwipeToDismissBoxState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState
import com.catshome.classJournal.domain.Group.Models.Group
import java.util.UUID


data class NewGroupState (
    var uid : String = UUID.randomUUID().toString(),
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
    var swipeToDismissBoxState: SwipeToDismissBoxState? = null,
    val group: Group
)

data class GroupState( //@OptIn(ExperimentalWearMaterialApi::class) constructor(
    var uidDelete: Long  = -1,
    var isDelete: Boolean =false,
    var swipeUid: Long = - 1,
    var orientation: Orientation = Orientation.Vertical,
    var orientationNew: Boolean = false,
   // var revealState: RevealState? = null,
    val listItems: List<GroupItem> =emptyList()
)

@OptIn(ExperimentalWearMaterialApi::class)
fun GroupState.mapGroup(group: List<Group>): List<GroupItem>{
    return  group.map { list-> GroupItem(null,null, list) }
}