package com.catshome.classJournal.Group.GroupRepositorys


import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(val roomGroupStorage: GroupStorage):GroupRepository {
    override  fun saveGroup(group: Group) {
        roomGroupStorage.insert(group)
    }

    override  fun deleteGroup(group: Group){
       roomGroupStorage.delete(group)
    }

    override  fun updateGroup(group: Group): Boolean {
        return roomGroupStorage.update(group)
    }

    override  fun getGroupById(uid: Long): Group {
            return  roomGroupStorage.getGroupById(uid)
    }

    override  fun getGroupByName(name: String): Group {
        return Group()
    }

    override fun getGroups(isDelete: Boolean): Flow<List<Group>> {
       return roomGroupStorage.read(isDelete)
    }
    override fun getAllGroups(): Flow<List<Group>> {
       return roomGroupStorage.readAll()
    }
}