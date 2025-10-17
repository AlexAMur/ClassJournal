package com.catshome.classJournal.screens.child

sealed class ChildListEvent {
    data object  NewGroupClicked: ChildListEvent()
    data object NewChildClicked: ChildListEvent()
    data object ReloadScreen: ChildListEvent()
    data class DeleteChildClicked(val uid: String,val index: Int) : ChildListEvent()
    data class DeleteGroupClicked(val uid: String) : ChildListEvent()
    data class UndoDeleteChildClicked(val uid: String, val index: Int) : ChildListEvent()
    data class UndoDeleteGroupClicked(val uid: String, val index: Int) : ChildListEvent()
}
