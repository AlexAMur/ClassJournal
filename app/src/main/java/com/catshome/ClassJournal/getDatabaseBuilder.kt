package com.catshome.ClassJournal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


fun getDatabaseBuilder(context: Context):RoomDatabase.Builder<AppDataBase>{
    val applicationContext =context
    val databasePath = applicationContext.getDatabasePath("classJournal.db")
    return Room.databaseBuilder(applicationContext, AppDataBase::class.java,"classJournal" )

}
