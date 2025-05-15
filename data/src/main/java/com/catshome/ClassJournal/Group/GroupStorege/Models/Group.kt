package com.catshome.ClassJournal.Group.GroupStorege.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
class Group(
@PrimaryKey(autoGenerate = true) val uId: Int,
@ColumnInfo( name = "group_name")val name: String,
val isDelete: Boolean
)