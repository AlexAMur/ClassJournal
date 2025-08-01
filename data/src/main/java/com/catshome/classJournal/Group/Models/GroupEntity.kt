package com.catshome.classJournal.Group.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey (autoGenerate = true) var uid: Long,
    @ColumnInfo(name = "group_name") var name: String = "",
    var isDelete: Boolean = false,

    )