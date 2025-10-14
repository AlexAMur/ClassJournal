package com.catshome.classJournal.Group.GroupRepositorys


import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(val roomGroupStorage: GroupStorage):GroupRepository {
    override suspend fun saveGroup(group: Group) {
               roomGroupStorage.insert(group)

    }

    override  fun deleteGroup(group: Group){
       roomGroupStorage.delete(group)
    }

    override  fun updateGroup(group: Group): Boolean {
        return roomGroupStorage.update(group)
    }

    override  fun getGroupById(uid: String): Group {
            return  roomGroupStorage.getGroupById(uid)
    }

    override  fun getGroupByName(name: String): Group {
        return Group()
    }

    override fun getGroups(isDelete: Boolean): Flow<List<Group>> {
       return roomGroupStorage.read(isDelete)
    }

    override fun getEmptyGroup() {
        TODO("Not yet implemented")
    }

    override fun getAllGroups(): Flow<List<Group>> {
       return roomGroupStorage.readAll()
    }
}