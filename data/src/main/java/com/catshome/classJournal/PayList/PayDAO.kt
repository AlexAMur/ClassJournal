package com.catshome.classJournal.PayList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDAO {
    @Insert ( onConflict = ABORT)
    suspend fun insert (payEntity: PayEntity)
   @Delete
   suspend fun delete (payEntity: PayEntity)
   @Update
   suspend fun update(payEntity: PayEntity)

   @Query("Select p.uid , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay " +
           ",p.pay  from pays p join child c where p.uid_child =c.uid and c.isDelete = :isDelete")
    fun getFull(isDelete: Boolean): Flow<List<PayScreenEntity>>?

    @Query("Select p.uid , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
            "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid")
    fun getPayByChildID(uid: String): Flow<List<PayScreenEntity>>?

    @Query("Select p.uid  , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
            "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid and " +
            "date_pay>= :begin and date_pay <= :end")
    fun getPayByChildIDWithPeriod(uid: String,begin: Long,end: Long): Flow<List<PayScreenEntity>>?


    @Query("select p.uid , p.uid_child ,  c.child_name as Name, c.child_surname as Surname, " +
            "p.date_pay, p.pay from pays p join child c where p.uid_child =c.uid " +
            "and c.isDelete = 0 and p.date_pay >= :begin and date_pay <= :end" )
    fun getPayByPeriod(begin: Long,end: Long): Flow<List<PayScreenEntity>>?

}

