package com.catshome.ClassJournal.Group.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups", primaryKeys = ["group_name", "isDelete"])
class GroupEntity(
    var uId: Int=-1,
    @ColumnInfo( name = "group_name") var name: String="",
    var isDelete: Boolean=false
)