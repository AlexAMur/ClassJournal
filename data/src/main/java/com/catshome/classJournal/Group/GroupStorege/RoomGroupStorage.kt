package com.catshome.classJournal.Group.GroupStorege

import android.util.Log
import com.catshome.classJournal.DAO.GroupsDAO
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
    val cs = CoroutineScope(Dispatchers.IO)
    override fun insert(group: Group) {
        cs.launch {
            groupsDAO.insert(group = group.mapToEntity())
        }
    }


    override fun delete(group: Group): Boolean {
        try {
            cs.launch {
                groupsDAO.delete(group.mapToEntity())
            }
            return true
        } catch (e: Exception) {
            Log.e("ClassJournal", e.message.toString())
            return false
        }
    }

    override fun update(group: Group): Boolean {
        try {
            cs.launch {
                groupsDAO.update(group.mapToEntity())
            }
            return true
        } catch (e: Exception) {
            Log.e("ClassJournal", e.message.toString())
            return false
        }
    }

    override  fun getGroupById(uid: String): Group {
        var group = Group()
        try {
            val data = cs.async {
                     return@async (groupsDAO.getGroupById(uid) ?: GroupEntity(uid = "")).mapToGroup()
            }
            runBlocking {
                 group =data.await()
            }
            return group
        } catch (e: Exception) {
            Log.e("ClassJournal", e.message.toString())
            return Group()
        }
            //TODO Все править
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