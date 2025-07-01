package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catshome.ClassJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface GroupsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(group: GroupEntity)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(group: GroupEntity)


    @Delete
   suspend fun delete(group: GroupEntity)

    @Query("Select * from 'groups'")
    fun getFull(): Flow<List<GroupEntity>>

    @Query("Select * from 'groups' where uid= :uid")
    fun getGroupById(uid: Long): GroupEntity?

    @Query("SELECT * FROM 'groups' where isDelete = :isDelete")
    fun getGroup(isDelete: Boolean): Flow<List<GroupEntity>>

}