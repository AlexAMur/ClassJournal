package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    fun saveChild(child: Child)
    fun updateChild(child: Child): Boolean
    fun deleteChild(child: Child)
    fun getChildById(uid: Long): Child
    fun getChildByName(name: String): Child
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
}