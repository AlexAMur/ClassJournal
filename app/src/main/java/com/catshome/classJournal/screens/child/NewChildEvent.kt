package com.catshome.classJournal.screens.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group

sealed class NewChildEvent {

    class OpenChild(val uid: String): NewChildEvent()
    class SaveChild(val child: Child): NewChildEvent()
    data object SaveClicked: NewChildEvent()
    data object ReloadScreen: NewChildEvent()
    data object CloseClicked: NewChildEvent()
    class SelectGroup(val uidGroup: String ): NewChildEvent()
}