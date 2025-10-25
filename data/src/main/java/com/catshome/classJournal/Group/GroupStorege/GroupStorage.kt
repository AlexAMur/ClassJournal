package com.catshome.classJournal.Group.GroupStorege

import com.catshome.classJournal.SQLError
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface GroupStorage {
    suspend fun insert(group: Group)
    suspend fun delete(group: Group)
    suspend fun update(group: Group)
    fun getGroupById(uid: String): Group
    fun read(isDelete: Boolean): Flow<List<Group>>
    fun readAll(): Flow<List<Group>>
}