package com.catshome.classJournal.screens.group

sealed class GroupAction {
    //TODO проверить воткрытие гуруппы почему тип LONG!!!
    class OpenGroup(val uid: Long = 0) : GroupAction()
    class DeleteGroup(val uid: Long) : GroupAction()
    class RequestDelete(val index: Int) : GroupAction()
}