package com.catshome.classJournal.screens.child

import androidx.compose.runtime.Composable

sealed class ChildListEvent {
    data object  NewGroupClicked: ChildListEvent()
    data object NewChildClicked: ChildListEvent()
    data class deleteClicked(val uidChild: String,val uidGroup:String, val key : String): ChildListEvent()
    data object ReloadScreen: ChildListEvent()
    data class deleteChild(val uid: String,val key : String) : ChildListEvent()
    data class deleteGroup(val uid: String) : ChildListEvent()
    data class ChangeRevealed(val item:ChildItem, val key : String,
                              val isOptionsRevealed: Boolean ) : ChildListEvent()
    data object undoDelete : ChildListEvent()
    data class showSnackBar(
        val message: String,
        val actionLabel: String,
        val onDismissed:  ()->Unit,
        val onActionPerformed: ()->Unit,

    ): ChildListEvent()

}
