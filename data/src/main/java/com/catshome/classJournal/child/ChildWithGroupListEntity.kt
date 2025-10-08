package com.catshome.classJournal.child

data class ChildWithGroupListEntity(
    val childUid : String,
    val childName: String,
    val childSurname: String,
    val groupUid: String?,
    val groupName: String?
)
