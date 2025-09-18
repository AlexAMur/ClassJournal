package com.catshome.classJournal.PayList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catshome.classJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDAO {
    @Insert ( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (pay: PayEntity)
   @Delete
   suspend fun delete (pay: PayEntity)
   @Query("Select * from 'groups'")
    fun getFull(): Flow<List<GroupEntity>>
}