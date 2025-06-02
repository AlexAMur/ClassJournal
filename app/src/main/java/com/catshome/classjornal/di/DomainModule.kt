package com.catshome.classjornal.di

import com.catshome.ClassJournal.AppDataBase
import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.GroupRepositorys.GroupRepositoryImpl
import com.catshome.ClassJournal.Group.GroupStorege.GroupStorage
import com.catshome.ClassJournal.Group.GroupStorege.RoomGroupStorage
import com.catshome.ClassJournal.domain.Group.GroupRepository
import com.catshome.ClassJournal.domain.Group.Models.Group
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
//    @Provides
//    fun provideGroupRepository():GroupRepository{
//        //groupStorage: GroupStorage
//        //groupStorage
//        return GroupRepositoryImpl()
//    }

}