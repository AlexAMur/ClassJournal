package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.catshome.ClassJournal.Group.GroupStorege.Models.Group

@Dao
interface GroupsDAO {
    @Insert
    fun insert(group: Group):Boolean
    @Delete
    fun delete(group: Group)

    @Query("Select * from groups")
    fun getAllFull(): List<Group>
    @Query("SELECT * FROM groups where isDelete = :isDelete")
    fun getAll(isDelete: Boolean): List<Group>

}