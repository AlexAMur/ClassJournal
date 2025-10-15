package com.catshome.classJournal.screens.child

import androidx.wear.compose.foundation.SwipeToDismissBoxState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealState
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.Models.Group

data class  ChildListState(
    var uidDelete: String = "",
    var isDelete: Boolean =false,
    var swipeUid: String = "",
    val item: Map<String ,List<ChildItem>> = emptyMap()
)

data class ChildItem @OptIn(ExperimentalWearMaterialApi::class) constructor(
    val index: Int = -1,
    var revealState: RevealState? = null,
    var swipeToDismissBoxState: SwipeToDismissBoxState? = null,
    val child: ChildWithGroups
)

@OptIn(ExperimentalWearMaterialApi::class)
fun ChildItem.copy(childWithGroups: ChildWithGroups){
    ChildItem(
        index= this.index,
        revealState = this.revealState,
        swipeToDismissBoxState= this.swipeToDismissBoxState,
        child = childWithGroups
    )
}