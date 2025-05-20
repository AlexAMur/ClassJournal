package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catshome.ClassJournal.Group.Models.GroupEntity

@Dao
interface GroupsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: GroupEntity)
    @Delete
    fun delete(group: GroupEntity)

    @Query("Select * from mygroups")
    fun getFull(): List<GroupEntity>
    @Query("SELECT * FROM mygroups where isDelete = :isDelete")
    fun getGroup(isDelete: Boolean): List<GroupEntity>

}