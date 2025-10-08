package com.catshome.classJournal.child

import android.util.Log
import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.String

class RoomChildStorage @Inject constructor(val childDAO: ChildDAO, val childGroup: ChildGroupDAO) :
    ChildStorage {

//    override fun insert(child: Child) {
//        CoroutineScope(Dispatchers.IO).launch {
//             childDAO.insert(child.mapToChildEntity())
//        }
//    }

    override fun insert(
        child: Child,
        childGroup: List<ChildGroup>
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            childDAO.insertChild(child.mapToChildEntity(), childGroup.map {
                           it.mapToChildGroupEntity()
            })
        }
    }

    override fun delete(child: Child) {
        CoroutineScope(Dispatchers.IO).launch {
            childDAO.delete(child.mapToChildEntity())
        }
    }

    override fun update(
        child: Child,
        childGroup: List<ChildGroup>
    ) {
        TODO("Not yet implemented")
    }

//    override fun update(child: Child) {
//        CoroutineScope(Dispatchers.IO).launch {
//            childDAO.update(child.mapToChildEntity())
//        }
//    }


    override fun getChildById(uid: String): Child {
        val defferedChild = CoroutineScope(Dispatchers.IO).async {
            return@async (childDAO.getChildById(uid))
        }
        val data = runBlocking {
            return@runBlocking defferedChild.await()
        }
        return data?.mapToChild() ?: Child()
    }

    override fun getChildByName(child: Child): Flow<List<Child>> {
        return childDAO.getChildByName(child.surname.toString()).map { list ->
            list.map { childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun read(isDelete: Boolean): Flow<List<Child>> {
        return childDAO.getChilds(isDelete).map { list ->
            list.map { childEntity ->
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

    override fun getChildWithGroups(isDelete: Boolean): List<ChildWithGroups> {
        val defferedChild = CoroutineScope(Dispatchers.IO).async {
            return@async childGroup.getChildAndGroups(isDelete).map {
                ChildWithGroups(
                    childUid = it.childUid,
                    childName = it.childName,
                    childSurname = it.childSurname,
                    groupUid = it.childUid,
                    groupName = it.groupName?:"Без группы"
                )
            }
        }
        val data = runBlocking {
            return@runBlocking defferedChild.await()
        }
        return data
    }
}
