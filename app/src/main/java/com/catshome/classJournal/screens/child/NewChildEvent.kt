package com.catshome.classJournal.screens.child

import com.catshome.classJournal.domain.Child.Child

sealed class NewChildEvent {

    class OpenChild(val uid: String): NewChildEvent()
    class SaveChild(val child: Child): NewChildEvent()
    data object SaveClicked: NewChildEvent()
    data object CloseClicked: NewChildEvent()
}