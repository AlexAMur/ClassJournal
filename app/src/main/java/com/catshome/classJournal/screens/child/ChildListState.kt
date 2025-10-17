package com.catshome.classJournal.screens.child

import com.catshome.classJournal.domain.Child.ChildWithGroups
import kotlin.collections.map

data class  ChildListState(
    var uidDelete: String = "",
    var isDelete: Boolean =false,
    var swipeUid: String = "",
    var item: MutableMap<String ,List<ChildItem>> = mutableMapOf()
)

data class ChildItem (
    val isOptionsRevealed: Boolean = false,
    val child: ChildWithGroups
)
fun ChangeOptionsRevealed(item: Map<String, List<ChildItem>>,
                          childItem: ChildItem, isOptionsRevealed: Boolean,
                          key: String):List<ChildItem>{

    return item.get(key)?.map {
        if (it == childItem)
            childItem.copy(isOptionsRevealed =  isOptionsRevealed)
        else
            it
    }?:emptyList()

}