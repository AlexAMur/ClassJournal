package com.catshome.classJournal.di

import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.GroupRepositorys.GroupRepositoryImpl
import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.Group.GroupStorege.RoomGroupStorage
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideGroupRepository(room: RoomGroupStorage): GroupRepository {
        return GroupRepositoryImpl(room)
    }
    @Provides
    fun provideGroupsDAO(room: AppDataBase): GroupsDAO {
        return room.groupsDAO()
    }
    @Provides
    fun provideGroup() = Group()
    @Provides
    fun provideGroupStorage(dao: GroupsDAO,group: Group): GroupStorage {
       //
         return RoomGroupStorage(dao, group )
    }
}