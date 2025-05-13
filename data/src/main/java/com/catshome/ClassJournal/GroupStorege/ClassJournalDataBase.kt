package com.catshome.ClassJournal.GroupStorege

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Models.Group

@Database(entities = [Group::class], version = 1)
abstract class ClassJournalDataBase: RoomDatabase() {
    abstract fun groupsDAO()
}