package com.catshome.classJournal.screens.child

sealed class ChildListAction {
    data object CloseScreen : ChildListAction()
    data object NewChildClicked: ChildListAction()
    data object NewGroupClicked: ChildListAction()
    data object StartScrool: ChildListAction()
    //data class SelectChildClicked(val uid: String): ChildListAction()
   // data object ReloadScreen: ChildListAction()
}