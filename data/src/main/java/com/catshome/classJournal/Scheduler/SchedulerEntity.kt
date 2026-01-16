package com.catshome.classJournal.Scheduler

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.child.ChildEntity

@Entity(
    tableName = "scheduler",
    indices = [Index(
        value = ["dayOfWeek", "startLesson"],
        name = "indexScheduler", unique = false
    )],
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("uidChild"),
            onDelete = CASCADE,
            deferred = true
        ),
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("uidGroup"),
            onDelete = CASCADE,
            deferred = true
        )
    ]
)
data class SchedulerEntity(
    @PrimaryKey val uid: String,
    val uidChild: String,
    val dayOfWeek: Int,
    val uidGroup: String,
    val startLesson: Long,
    val duration: Int
)