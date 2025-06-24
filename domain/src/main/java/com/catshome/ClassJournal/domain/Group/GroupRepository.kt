package com.catshome.ClassJournal.domain.Group

import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
  fun saveGroup(group: Group):Boolean
 fun updateGroup(group: Group):Boolean
 fun deleteGroup(group: Group):Boolean

 fun getGroupById(uid:Int):Group
 fun getGroupByName(name:String):Group
 fun getGroups(isDelete:Boolean):Flow<List<Group>>
}