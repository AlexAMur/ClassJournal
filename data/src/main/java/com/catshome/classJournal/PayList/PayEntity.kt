package com.catshome.classJournal.PayList

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
@Entity
data class PayEntity(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val uid_child: String,
    val date_pay: Long,
    val pay: Int
)
