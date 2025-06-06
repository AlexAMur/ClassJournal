package com.catshome.ClassJournal.Group.GroupStorege

import android.annotation.SuppressLint
import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.GroupEntity
import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

fun Group.mapToEntity(): GroupEntity {
    return GroupEntity(
        uId = this.uId,
        name = this.name,
        isDelete = this.isDelete
    )
}

fun GroupEntity.mapToGroup(): Group {
    return Group(
        uId = this.uId,
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
        return true
    }

    override fun update(group: Group): Boolean {
        return true
    }


    override suspend fun read(): List<Group> {
        val list = groupsDAO.getGroup(false)
            .collect { entity -> entity.forEach { it.mapToGroup() } } as List<Group>
        return list
    }
}