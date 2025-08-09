package com.catshome.classJournal.child

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child")
data class ChildEntity(
    @PrimaryKey(autoGenerate = true) var uid: Long,
    @ColumnInfo(name = "child_name") var name: String,
    @ColumnInfo(name = "child_surname") var surname: String,
    @ColumnInfo(name = "child_phone") var phone: String,
    @ColumnInfo(name = "child_note") var note: String,
    @ColumnInfo(name = "child_birthday") var birthday: Long,
    var isDelete: Boolean = false
)