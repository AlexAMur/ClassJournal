package com.catshome.classJournal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

var databaseDir: File? = null
fun getDatabaseBuilder(context: Context):RoomDatabase.Builder<AppDataBase>{
    val applicationContext =context
    databaseDir = applicationContext.getDatabasePath("classJournal.db").parentFile
    return Room.databaseBuilder(applicationContext, AppDataBase::class.java,"classJournal" )
}
