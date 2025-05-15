package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catshome.ClassJournal.Group.Models.Group

@Dao
interface GroupsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: Group):Boolean
    @Delete
    fun delete(group: Group)

    @Query("Select * from groups")
    fun getFull(): List<Group>
    @Query("SELECT * FROM groups where isDelete = :isDelete")
    fun getGroup(isDelete: Boolean): List<Group>

}