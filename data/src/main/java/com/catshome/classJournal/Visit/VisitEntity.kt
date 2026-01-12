package com.catshome.classJournal.Visit

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.Child.MiniChild

@Entity(
        tableName = "visits",
        indices = [Index(
            value = ["dateVisit"],
            name = "indexVisit", unique = false
        )],
        foreignKeys = [ForeignKey(
            entity = ChildEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("uidChild"),
            onDelete = CASCADE,
            deferred = true
        )]
)
data class VisitEntity(
    @PrimaryKey val uid: String,
    val uidChild: String,
    val dateVisit: Long,
    val priceVisit: Int
)

data class VisitScreenEntity(
    val uid: String,
    val uidChild: String,
    val fio: String,
    val dateVisit: Long,
    val priceVisit: Int
)
//class VisitScreenEntity{
//    @Embedded
//    var child: MiniChild? =null
//    @Relation
//        (parentColumn = "uid",
//                entityColumn = "uidChild")
//    var visit: List<VisitEntity> = ArrayList()
//}

