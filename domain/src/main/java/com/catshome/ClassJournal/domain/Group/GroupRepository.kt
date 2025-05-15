package com.catshome.ClassJournal.domain.Group

import com.catshome.ClassJournal.domain.Group.Models.Group

interface GroupRepository {
  suspend  fun saveGroup(group: Group):Boolean
    suspend fun deleteGroup(group: Group):Boolean
    suspend fun updateGroup(group: Group):Boolean
    suspend  fun getGroupById(uid:Int):Group
    suspend  fun getGroupByName(name:String):Group
    suspend  fun getGroups(isDelete:Boolean):List<Group>
}