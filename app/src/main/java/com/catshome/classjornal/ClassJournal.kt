package com.catshome.classjornal

import android.app.Application
import androidx.room.Room
import com.catshome.ClassJournal.ClassJournalDataBase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class ClassJournal: Application(){
     val dataBase =Room.databaseBuilder(applicationContext, ClassJournalDataBase::class.java, "classJournal").build()
}