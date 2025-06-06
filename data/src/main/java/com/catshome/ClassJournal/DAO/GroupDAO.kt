package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catshome.ClassJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(group: GroupEntity)
    @Delete
   suspend fun delete(group: GroupEntity)

    @Query("Select * from groups")
    fun getFull(): Flow<List<GroupEntity>>
    @Query("SELECT * FROM groups where isDelete = :isDelete")
    fun getGroup(isDelete: Boolean): Flow<List<GroupEntity>>

}