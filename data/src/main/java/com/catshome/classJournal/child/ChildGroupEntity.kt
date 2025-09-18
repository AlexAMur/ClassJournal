package com.catshome.classJournal.child

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "child_group")
data class ChildGroupEntity(
  @PrimaryKey  val uid: String = UUID.randomUUID().toString(),
    val childId: String,
    val groupId: String,
)
