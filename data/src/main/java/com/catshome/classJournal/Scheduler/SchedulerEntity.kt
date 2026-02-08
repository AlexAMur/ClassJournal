package com.catshome.classJournal.Scheduler

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import java.util.UUID
import kotlin.uuid.Uuid


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
    val uidChild: String?,
    val dayOfWeek: Int,
    val uidGroup: String?,
    val startLesson: Long,
    val duration: Int
)

data class SchedulerScreenEntity(
    @PrimaryKey val uid: String,
    val uidChild: String?,
    val dayOfWeek: Int,
    val uidGroup: String?,
    val name: String?,
    val groupName: String?,
    val startLesson: Long,
    val duration: Int
)
fun SchedulerScreenEntity.mapToScheduler(): Scheduler{
 return Scheduler(
     uid = this.uid,
     dayOfWeek = DayOfWeek.entries[this.dayOfWeek].shortName,
     dayOfWeekInt = this.dayOfWeek,
     uidChild = this.uidChild,
     uidGroup = this.uidGroup,
     name = this.name?:this.groupName?:"",
     startLesson = this.startLesson,
     duration = this.duration
 )
}
fun Scheduler.mapToSchedulerEntity(): SchedulerEntity{
    return SchedulerEntity(
        uid = this.uid?: UUID.randomUUID().toString(),
        uidChild = this.uidChild,
        dayOfWeek = this.dayOfWeekInt,
        uidGroup = this.uidGroup,
        startLesson = this.startLesson?:0,
        duration = this.duration?:0
    )
}