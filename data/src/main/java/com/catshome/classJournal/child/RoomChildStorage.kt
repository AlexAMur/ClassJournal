package com.catshome.classJournal.child

import android.util.Log
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomChildStorage @Inject constructor(val childDAO: ChildDAO, val group: Group) :
    ChildStorage {
    override fun insert(child: Child) {
        Log.e("CLJR", "uid = ${child.uid}, name=  ${child.name}, birthday=  ${child.birthday} ")
        CoroutineScope(Dispatchers.IO).launch {
            childDAO.insert(child.mapToChildEntity())
        }
    }

    override fun delete(child: Child) {
        CoroutineScope(Dispatchers.IO).launch {
            childDAO.delete(child.mapToChildEntity())
        }
    }

    override fun update(child: Child) {
        CoroutineScope(Dispatchers.IO).launch {
            childDAO.update(child.mapToChildEntity())
        }
    }


    override fun getChildById(uid: String): Child {
        return childDAO.getChildById(uid)?.mapToChild() ?: Child()

    }

    override fun getChildByName(child: Child): Flow<List<Child>> {
        return childDAO.getChildByName(child.surname.toString()).map { list ->
            list.map {childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun read(isDelete: Boolean): Flow<List<Child>> {
        return childDAO.getChilds(isDelete).map {list ->
            list.map {childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun readAll(): Flow<List<Child>> {
        return childDAO.getFull().map { list ->
            list.map { childEntity ->
                childEntity.mapToChild()
            }
        }
    }
}