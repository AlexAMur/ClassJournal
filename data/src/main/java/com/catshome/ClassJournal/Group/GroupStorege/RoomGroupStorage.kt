package com.catshome.ClassJournal.Group.GroupStorege

import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.domain.Group.Models.Group
import javax.inject.Inject

class RoomGroupStorage@Inject constructor(val groupsDAO: GroupsDAO, val group: Group) {
    fun insertGroup(group: Group):Boolean{
       return groupsDAO.insert(group = this.group)
    }
}