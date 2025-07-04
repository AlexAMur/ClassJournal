package com.catshome.classJournal.screens.group

sealed class GroupAction {
    class OpenGroup(val uid: Long = 0) : GroupAction()
    class DeleteGroup(val uid: Long) : GroupAction()
    object RequestDelete : GroupAction()
}