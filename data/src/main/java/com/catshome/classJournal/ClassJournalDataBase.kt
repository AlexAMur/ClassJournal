package com.catshome.classJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.child.ChildGroupEntity

@Database(entities = [GroupEntity::class, ChildEntity::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun groupsDAO():GroupsDAO
    abstract fun childDAO(): ChildDAO
    abstract fun childGroupDAO(): ChildGroupDAO
}

internal val MIGRATION_2_3 = object : Migration (2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("")
    }
    
}