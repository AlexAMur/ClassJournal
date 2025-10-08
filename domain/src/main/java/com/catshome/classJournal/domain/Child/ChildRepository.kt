package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    fun saveChild(child: Child, childGroup: List<ChildGroup>)
    fun updateChild(child: Child, childGroup: List<ChildGroup>)
    fun deleteChild(child: Child)
    fun checkDeleteChild(child: Child , isDelete: Boolean)
    fun getChildById(uid: String): Child
    fun getChildByName(name: String): Child
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
    fun getChildWithGroups(): List<ChildWithGroups>
    //fun getChildGroups(UID: String ): Flow<List<ChildGroup>>
}