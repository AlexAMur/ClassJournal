package com.catshome.classJournal.screens.child

sealed class NewChildAction {
    data object CloseClicked : NewChildAction()
    data object SaveClicked: NewChildAction()
    data object SelectDocs: NewChildAction()
}