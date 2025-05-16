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

class RoomGroupStorage@Inject constructor(val groupsDAO: GroupsDAO, val group: Group) {

fun insertGroup(group: Group):Boolean{
       return groupsDAO.insert(group = group.mapToEntity())
    }
}