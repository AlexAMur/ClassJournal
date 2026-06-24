package com.catshome.classJournal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File


fun getDatabaseBuilder(context: Context):RoomDatabase.Builder<AppDataBase>{
    val applicationContext =context

    return Room.databaseBuilder(applicationContext, AppDataBase::class.java,"classJournal" )
}
