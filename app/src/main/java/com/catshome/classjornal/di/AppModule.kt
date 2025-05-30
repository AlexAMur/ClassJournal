package com.catshome.ClassJournal.com.catshome.classjornal.di

import android.content.Context
import com.catshome.ClassJournal.AppDataBase
import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.domain.Group.GroupRepository
import com.catshome.classjornal.getDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context):AppDataBase{
        return getDatabaseBuilder(context = context).build()
    }
//    @Provides
//    fun provideGroupInteractor(repository: GroupRepository): GroupInteractor {
//        return GroupInteractor(repository)
//    }
}