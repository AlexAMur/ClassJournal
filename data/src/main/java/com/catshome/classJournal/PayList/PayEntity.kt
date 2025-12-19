package com.catshome.classJournal.PayList

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.communs.toDateStringRU
import com.catshome.classJournal.domain.communs.toLocalDateTime
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.domain.communs.toDateTimeRuString

@Entity(
    tableName = "pays",
    indices = [Index(
        value = ["uid_child", "date_pay", "pay"],
        name = "paysindex", unique = false
    )],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("uid_child"),
            onDelete = CASCADE,
            deferred = true
        )
    )
)
data class PayEntity(
    @PrimaryKey val uid: String,
    val uid_child: String,
//    @Ignore val Name: String,
//    @Ignore val Surname: String,
    val date_pay: Long,
    val pay: Int
)

data class PayScreenEntity(
    val uid: String,
    val uid_child: String,
    val Name: String,
    val Surname: String,
    val date_pay: Long,
    val pay: Int
)

fun PayEntity.mapToPay(): Pay {
    return Pay(
        uidPay = this.uid,
        uidChild = this.uid_child,
//       Name =  this.Name,
//       Surname = this.Surname,
        datePay = this.date_pay.toDateStringRU(),
        payment = pay.toString()
    )
}

fun PayScreenEntity.mapToPay(): Pay {
    return Pay(
        uidPay = this.uid,
        uidChild = this.uid_child,
        name = this.Name,
        surName = this.Surname,
        datePay = this.date_pay.toLocalDateTimeRu().toDateTimeRuString(),
        payment = pay.toString()
    )
}

fun Pay.mapToPayEntity(): PayEntity {
    return PayEntity(
        uid = this.uidPay,
        uid_child = this.uidChild,
        date_pay = this.datePay.toLocalDateTime()?.toLong()?:0,
        pay = this.payment.toInt(),
    )
}
