package com.catshome.classJournal.di

import android.content.Context
import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.getDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return getDatabaseBuilder(context = context).fallbackToDestructiveMigration(dropAllTables = true)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    }
}