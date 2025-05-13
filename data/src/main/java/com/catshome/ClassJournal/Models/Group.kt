package com.catshome.ClassJournal.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val uId: Int,
    @ColumnInfo( name = "group_name")val name: String,
    val isDelete: Boolean
    )
