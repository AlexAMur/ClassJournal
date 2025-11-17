package com.catshome.classJournal.PayList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDAO {
    @Insert ( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (payEntity: PayEntity)
   @Delete
   suspend fun delete (payEntity: PayEntity)
   @Update
   suspend fun update(payEntity: PayEntity)

   @Query("Select * from payEntity")
    fun getFull(): Flow<List<PayEntity>>?

    @Query("Select * from payEntity where uid_child = :uid")
    fun getPayByChildID(uid: String): Flow<List<PayEntity>>?

    @Query("Select * from payEntity where uid_child = :uid and date_pay >= :begin and date_pay <= :end")
    fun getPayByChildIDWithPeriod(uid: String,begin: Long,end: Long): Flow<List<PayEntity>>?

    @Query("Select * from payEntity where date_pay >= :begin and date_pay <= :end")
    fun getPayByPeriod(begin: Long,end: Long): Flow<List<PayEntity>>?
}