package com.catshome.classJournal.screens.child

sealed class NewChildAction {
    class SaveChild(): NewChildAction()
    data object CloseClicked : NewChildAction()
    data object SaveClicked: NewChildAction()
}