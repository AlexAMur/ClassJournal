package com.catshome.classJournal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.PayList.PayDAO
import com.catshome.classJournal.PayList.PayEntity
import com.catshome.classJournal.Visit.VisitDAO
import com.catshome.classJournal.Visit.VisitEntity

import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.child.ChildGroupEntity

@Database(entities = [
    GroupEntity::class,
    ChildEntity::class,
    ChildGroupEntity::class,
    PayEntity::class,
    VisitEntity::class],
    version = 7)
abstract class AppDataBase : RoomDatabase() {
    abstract fun groupsDAO(): GroupsDAO
    abstract fun childDAO(): ChildDAO
    abstract fun childGroupDAO(): ChildGroupDAO
    abstract fun payDAO(): PayDAO

    abstract fun visitDAO(): VisitDAO

    internal val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {

            db.execSQL("")
        }
    }


}