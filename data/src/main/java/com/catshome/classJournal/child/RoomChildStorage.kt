package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomChildStorage @Inject constructor(val chidDAO: ChildDAO, val group: Group) :
        ChildStorage {
    override fun insert(child: Child) {
        CoroutineScope(Dispatchers.IO).launch {
            chidDAO.insert(child.mapToChildEntity())
        }
    }

    override fun delete(child: Child) {
        CoroutineScope(Dispatchers.IO).launch {
            chidDAO.insert(child.mapToChildEntity())
        }
    }

//    override fun update(child: Child): Boolean {
//        TODO("Not yet implemented")
//    }

    override fun getChildById(uid: String): Child {
        TODO("Not yet implemented")
    }

    override fun getChildByName(child: Child): Child {
        TODO("Not yet implemented")
    }

    override fun read(isDelete: Boolean): Flow<List<Child>> {
      return chidDAO.getFull().collect { it.forEach { childEntity-> childEntity.mapToChild() } }
    }

    override fun readAll(): Flow<List<Child>> {
        TODO("Not yet implemented")
    }

}