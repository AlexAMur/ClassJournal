package com.catshome.ClassJournal.domain.Group

import com.catshome.ClassJournal.domain.Group.Models.Group

interface GroupRepository {
  fun saveGroup(group: Group):Boolean
 fun updateGroup(group: Group):Boolean
 fun deleteGroup(group: Group):Boolean

 fun getGroupById(uid:Int):Group
 fun getGroupByName(name:String):Group
 suspend fun getGroups(isDelete:Boolean):List<Group>
}