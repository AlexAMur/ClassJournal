package com.catshome.ClassJournal.Group.GroupRepositorys


import com.catshome.ClassJournal.Group.GroupStorege.GroupStorage
import com.catshome.ClassJournal.Group.GroupStorege.RoomGroupStorage
import com.catshome.ClassJournal.domain.Group.GroupRepository
import com.catshome.ClassJournal.domain.Group.Models.Group
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(val roomGroupStorage: GroupStorage):GroupRepository {
    override  fun saveGroup(group: Group): Boolean {

        roomGroupStorage.insert(group)

        return true
    }

    override  fun deleteGroup(group: Group): Boolean {
        return true
    }

    override  fun updateGroup(group: Group): Boolean {

        return true//roomGroupStorage.update(group)
    }

    override  fun getGroupById(uid: Int): Group {
        return Group()
    }

    override  fun getGroupByName(name: String): Group {
        return Group()
    }

    override suspend  fun getGroups(isDelete: Boolean): List<Group> {
        return roomGroupStorage.read()
    }
}