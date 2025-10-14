package com.catshome.classJournal.domain.Group

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
     suspend fun saveGroup(group: Group)
    fun updateGroup(group: Group): Boolean
    fun deleteGroup(group: Group)
    fun getGroupById(uid: String): Group
    fun getGroupByName(name: String): Group
    fun getGroups(isDelete: Boolean =false): Flow<List<Group>>
    fun getEmptyGroup()
    fun getAllGroups(): Flow<List<Group>>
}