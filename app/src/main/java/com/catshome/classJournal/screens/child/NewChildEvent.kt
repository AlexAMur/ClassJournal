package com.catshome.classJournal.screens.child

import com.catshome.classJournal.domain.Child.Child

sealed class NewChildEvent {
    class OpenChild(val uid: Long): NewChildEvent()
    class SaveChild(val child: Child): NewChildEvent()
}