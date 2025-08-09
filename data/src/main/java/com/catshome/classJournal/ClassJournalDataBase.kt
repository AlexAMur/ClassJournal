package com.catshome.classJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildEntity

@Database(entities = [GroupEntity::class, ChildEntity::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun groupsDAO():GroupsDAO
    abstract fun childDAO(): ChildDAO
}