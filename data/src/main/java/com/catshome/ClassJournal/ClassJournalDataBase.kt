package com.catshome.ClassJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.Group

@Database(entities = [Group::class], version = 1)
abstract class ClassJournalDataBase: RoomDatabase() {
    abstract fun groupsDAO():GroupsDAO
}