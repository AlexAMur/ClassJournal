package com.catshome.classJournal.domain.Group

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun saveGroup(group: Group)
    fun updateGroup(group: Group): Boolean
    fun deleteGroup(group: Group)
    fun getGroupById(uid: Long): Group
    fun getGroupByName(name: String): Group
    fun getGroups(isDelete: Boolean): Flow<List<Group>>
    fun getAllGroups(): Flow<List<Group>>
}