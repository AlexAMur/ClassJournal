package com.catshome.ClassJournal.Group.GroupStorege

import com.catshome.ClassJournal.DAO.GroupsDAO
import com.catshome.ClassJournal.Group.Models.GroupEntity
import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
fun Group.mapToEntity():GroupEntity {
    return GroupEntity(
         uId =  this.uId,
        name = this.name,
        isDelete = this.isDelete
    )
}
fun GroupEntity.mapToGroup():Group{
    return Group(
        uId =  this.uId,
        name = this.name,
        isDelete = this.isDelete
    )
}

class RoomGroupStorage @Inject constructor ( val groupsDAO: GroupsDAO,val group: Group) : GroupStorage{
    val cs =CoroutineScope(Dispatchers.IO)
    override fun insert(group: Group)  {

            cs.launch{
            groupsDAO.insert(group = group.mapToEntity())
        }
    }

    override fun delete(group: Group): Boolean {
       return true
    }

    override fun update(group: Group): Boolean {
        return true
    }

    override fun read(): List<Group> {
        cs.launch {
       val g =  groupsDAO.getGroup(false).forEach { it.mapToGroup() }
        }

        return emptyList()
    }
}