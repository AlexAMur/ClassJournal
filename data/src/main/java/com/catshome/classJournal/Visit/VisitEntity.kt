package com.catshome.classJournal.Visit

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toLong
import java.util.UUID
import kotlin.uuid.Uuid

@Entity(
        tableName = "visits",
        indices = [Index(
            value = ["dateVisit"],
            name = "indexVisit",
            unique = false
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
    val Name: String,
    val Surname: String,
    val dateVisit: Long,
    val priceVisit: Int
)

fun Visit.mapToVisitEntity(): VisitEntity{
    if (this.data== null)
        throw IllegalArgumentException("Не допустимое значение visit date ${ this.data}")
    if (this.price == null)
        throw IllegalArgumentException("Не допустимое значение visit.price ${ this.price}")
    return VisitEntity(uid = this.uid?: UUID.randomUUID().toString(),
        uidChild = this.uidChild.toString(),
        dateVisit = this.data?.toLocalDateTimeRu()?.toLong()?:0,
        priceVisit = this.price?:0)
}
fun VisitScreenEntity.mapToVisit(): Visit {
    return Visit(
        uid = this.uid,
        uidChild = this.uidChild,
        fio = "${this.Surname} ${this.Name}",
        price = this.priceVisit,
        startLesson = 0,
        data = this.dateVisit.toLocalDateTimeRuString().toString(),
        check = false
    )
}

