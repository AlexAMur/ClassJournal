package com.catshome.classJournal.Group.GroupStorege

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface GroupStorage {
    fun insert(group: Group)
    fun delete(group: Group):Boolean
    fun update(group: Group):Boolean
    fun getGroupById(uid: Long): Group
    fun read(isDelete: Boolean): Flow<List<Group>>
    fun readAll(): Flow<List<Group>>
}