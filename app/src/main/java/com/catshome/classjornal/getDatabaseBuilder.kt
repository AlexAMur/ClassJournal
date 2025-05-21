package com.catshome.classjornal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catshome.ClassJournal.AppDataBase


fun getDatabaseBuilder(context: Context):RoomDatabase.Builder<AppDataBase>{
    val applicationContext =context
    val databasePath = applicationContext.getDatabasePath("classJournal.db")
    return Room.databaseBuilder(applicationContext, AppDataBase::class.java,databasePath.absolutePath )
        .fallbackToDestructiveMigration(dropAllTables = true)
}
