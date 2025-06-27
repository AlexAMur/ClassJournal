package com.catshome.ClassJournal.di

import android.content.Context
import com.catshome.ClassJournal.AppDataBase
import com.catshome.ClassJournal.GroupScreen
import com.catshome.ClassJournal.Screens.viewModels.GroupViewModel
import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.getDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

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