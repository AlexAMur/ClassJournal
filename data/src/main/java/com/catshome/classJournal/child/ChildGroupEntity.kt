package com.catshome.classJournal.child

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.PrimaryKey
import com.catshome.classJournal.domain.Child.ChildGroup
import java.util.UUID

@Entity(
    tableName = "child_group",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("childId"),
            deferred = true
        )
    )
)
data class ChildGroupEntity(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val childId: String,
    val groupId: String,
)
fun ChildGroup.mapToChildGroupEntity(): ChildGroupEntity {
    return  ChildGroupEntity(uid =  this.uid,
        childId =   this.childId,
        groupId =   this.groupId)
}
fun ChildGroupEntity.mapToChildGroup(): ChildGroup{
    return ChildGroup(
        uid = this.uid,
        childId =   this.childId,
        groupId =   this.groupId)

}