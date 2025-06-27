package com.catshome.ClassJournal.Screens.Group

sealed class GroupAction {
    class OpenGroup(val uid: Long = 0) : GroupAction()
    class DeleteGroup(val uid: Long) : GroupAction()
}