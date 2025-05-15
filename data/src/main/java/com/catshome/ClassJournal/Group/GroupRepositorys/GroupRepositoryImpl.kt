package com.catshome.ClassJournal.Group.GroupRepositorys


import com.catshome.ClassJournal.Group.GroupStorege.RoomGroupStorage
import com.catshome.ClassJournal.domain.Group.GroupRepository
import com.catshome.ClassJournal.domain.Group.Models.Group
import javax.inject.Inject

class GetGroupRepositoryImpl @Inject constructor(val roomGroupStorage: RoomGroupStorage): GroupRepository {
    override suspend fun saveGroup(group: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(group: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroup(group: Group): Boolean {

        return roomGroupStorage.insertGroup(group)
    }

    override suspend fun getGroupById(uid: Int): Group {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupByName(name: String): Group {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(isDelete: Boolean): List<Group> {
        TODO("Not yet implemented")
    }


}