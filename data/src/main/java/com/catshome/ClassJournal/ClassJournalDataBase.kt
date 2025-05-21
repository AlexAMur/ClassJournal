package com.catshome.ClassJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.GroupEntity

@Database(entities = [GroupEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun groupsDAO():GroupsDAO
}