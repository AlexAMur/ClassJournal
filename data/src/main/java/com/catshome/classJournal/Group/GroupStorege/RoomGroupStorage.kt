package com.catshome.classJournal.Group.GroupStorege

import android.util.Log
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.SQLError
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


fun Group.mapToEntity(): GroupEntity {
    return GroupEntity(
        uid = this.uid,
        name = this.name,
        isDelete = this.isDelete,
    )
}

fun GroupEntity.mapToGroup(): Group {
    return Group(
        uid = this.uid,
        name = this.name,
        isDelete = this.isDelete
    )
}

class RoomGroupStorage @Inject constructor(val groupsDAO: GroupsDAO, val group: Group) :
    GroupStorage {

    override suspend fun insert(group: Group) {
        if (groupsDAO.getGroupById(group.uid) == null)
            groupsDAO.insert(group = group.mapToEntity())
        else
            groupsDAO.update(group.mapToEntity())
    }

    override suspend fun delete(group: Group)  {
         groupsDAO.deleteGroup(group.mapToEntity())
    }

    override suspend fun update(group: Group){
         groupsDAO.update(group.mapToEntity())
    }

    override fun getGroupById(uid: String): Group {
        Log.e("CLJR", "getGroupByID uid = $uid")
        val cs = CoroutineScope(Dispatchers.IO)
        var group = Group()
        val data = cs.async {
            return@async(groupsDAO.getGroupById(uid) ?: GroupEntity(uid = "")).mapToGroup()
        }
       runBlocking {
           group =data.await()
        }
        return group
    }

    override fun readAll(): Flow<List<Group>> {
        return groupsDAO.getFull().map { list ->
            list.map { it.mapToGroup() }
        }
    }

    override fun read(isDelete: Boolean): Flow<List<Group>> {
        return groupsDAO.getGroup(isDelete).map { list ->
            list.map { it.mapToGroup() }
        }
    }
}