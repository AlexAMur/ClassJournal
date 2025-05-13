package com.catshome.ClassJournal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.catshome.ClassJournal.Models.Group

@Dao
interface GroupsDAO {
    @Insert
    fun insertAll(vararg group: Group)
    @Delete
    fun delete(vararg group: Group)

    @Query("Select * from groups")
    fun getAllFull(): List<Group>
    @Query("SELECT * FROM groups where isDelete = :isDelete")
    fun getAll(isDelete: Boolean): List<Group>

}