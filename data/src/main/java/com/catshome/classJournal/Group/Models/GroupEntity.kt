package com.catshome.classJournal.Group.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "groups" ,indices = [Index(value = ["group_name"], unique = true)])
data class GroupEntity(
    @PrimaryKey  var uid: String,
    @ColumnInfo(name = "group_name") var name: String = "",
    var isDelete: Boolean = false,

    )