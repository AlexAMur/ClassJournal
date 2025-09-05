package com.catshome.classJournal.child

import androidx.room.Entity

@Entity(tableName = "child_group")
data class ChildGroupEntity(
    val uid: String,
    val childId: String,
    val groupId: String,
)
