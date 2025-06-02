package com.catshome.classjornal.di

import androidx.room.Room
import androidx.room.RoomDatabase
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