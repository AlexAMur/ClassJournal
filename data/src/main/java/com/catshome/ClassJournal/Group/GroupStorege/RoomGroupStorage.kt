package com.catshome.ClassJournal.Group.GroupStorege

import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.GroupEntity
import com.catshome.ClassJournal.domain.Group.Models.Group
import javax.inject.Inject
fun Group.mapToEntity():GroupEntity {
    return GroupEntity(
         uId =  this.uid,
        name = this.name,
        isDelete = this.isDelete
    )
}

class RoomGroupStorage @Inject constructor (val groupsDAO: GroupsDAO, val group: Group) : GroupStorage{

    override fun insert(group: Group): Boolean {
        groupsDAO.insert(group = group.mapToEntity())
        return true
    }

    override fun delete(group: Group): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(group: Group): Boolean {
        TODO("Not yet implemented")
    }

    override fun read(): List<Group> {
        TODO("Not yet implemented")
    }
}