package com.catshome.classJournal.PayList

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.communs.toDateRu
import com.catshome.classJournal.domain.communs.toDateStringRU

@Entity(tableName = "pays",
    indices = [Index(value = ["uid_child","date_pay",  "pay"], unique = true)],
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
    val date_pay: Long,
    val pay: Int
)
fun PayEntity.mapToPay(): Pay{
   return Pay(
        uidPay = this.uid,
        uidChild = this.uid_child,
        datePay = this.date_pay.toDateStringRU(),
        payment = pay.toString()
    )
}
fun Pay.mapToPayEntity(): PayEntity{
    return PayEntity(
        uid = this.uidPay,
        uid_child = this.uidChild,
        date_pay = this.datePay.toDateRu().time,
        pay = this.payment.toInt())

}
