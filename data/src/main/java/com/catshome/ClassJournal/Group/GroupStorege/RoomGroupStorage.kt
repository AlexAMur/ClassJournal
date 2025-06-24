package com.catshome.ClassJournal.Group.GroupStorege

import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.GroupEntity
import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
        return true
    }

    override fun update(group: Group): Boolean {
        return true
    }

    override fun getById(uid: Int): Group {
        return groupsDAO.getGroupById(uid).mapToGroup()
    }


    override  fun read(): Flow<List<Group>> {
//        var listGroup: MutableList<Group> = mutableListOf()
//        cs.launch {
//         listGroup.add(groupsDAO.getGroup(false)
//            .collect { entity -> entity.forEach { it.mapToGroup() } } as Group)
//        }
//        return listGroup
     return groupsDAO.getGroup(false).map {
         list-> list.map { it.mapToGroup()}
     }
    }
}