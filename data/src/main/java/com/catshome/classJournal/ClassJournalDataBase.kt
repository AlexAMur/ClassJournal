package com.catshome.classJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity

@Database(entities = [GroupEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun groupsDAO():GroupsDAO
}