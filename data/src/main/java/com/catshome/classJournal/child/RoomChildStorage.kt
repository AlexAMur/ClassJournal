package com.catshome.classJournal.child

import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomChildStorage @Inject constructor(val groupsDAO: GroupsDAO, val group: Group) :
        ChildStorage {
    override fun insert(child: Child) {
        TODO("Not yet implemented")
    }

    override fun delete(child: Child): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(child: Child): Boolean {
        TODO("Not yet implemented")
    }

    override fun getChildById(uid: Long): Child {
        TODO("Not yet implemented")
    }

    override fun getChildByName(child: Child): Child {
        TODO("Not yet implemented")
    }

    override fun read(isDelete: Boolean): Flow<List<Child>> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Flow<List<Child>> {
        TODO("Not yet implemented")
    }

}