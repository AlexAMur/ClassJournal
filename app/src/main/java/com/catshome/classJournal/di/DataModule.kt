package com.catshome.classJournal.di

import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.GroupRepositorys.GroupRepositoryImpl
import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.Group.GroupStorege.RoomGroupStorage
import com.catshome.classJournal.PayList.PayDAO
import com.catshome.classJournal.PayList.PayListRepositoryImpl
import com.catshome.classJournal.PayList.RoomPayStorage
import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.child.ChildRepositoryImpl
import com.catshome.classJournal.child.RoomChildStorage
import com.catshome.classJournal.domain.Child.ChildGroupRepository
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.domain.PayList.PayListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providePayListRepository(room: RoomPayStorage): PayListRepository{
        return PayListRepositoryImpl(room)
    }
    @Provides
    fun provideChildRepository(room: RoomChildStorage): ChildRepository {
        return ChildRepositoryImpl(room)
    }
    @Provides
    fun provideGroupRepository(room: RoomGroupStorage): GroupRepository {
        return GroupRepositoryImpl(room)
   }
    @Provides
    fun provideChildGroupRepository(childGroupDAO: ChildGroupDAO): ChildGroupRepository {
        return ChildGroupsRepositoryImpl(childGroupDAO)
    }
    @Provides
    fun provideChildDAO(room: AppDataBase): ChildDAO{
        return room.childDAO()
    }

        @Provides
    fun providePayDAO(room: AppDataBase): PayDAO{
        return room.payDAO()
    }

    @Provides
    fun provideChildGroupDAO(room: AppDataBase): ChildGroupDAO{
        return room.childGroupDAO()
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