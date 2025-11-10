package com.catshome.classJournal.domain.Child

import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    suspend fun saveChild(child: Child, childGroup: List<ChildGroup>)
    suspend fun updateChild(child: Child)
    suspend fun deleteSet(child: Child)
   // fun checkDeleteChild(child: Child , isDelete: Boolean)
    fun getChildById(uid: String): Child?
    fun childDeleteExists(child: Child): Child?
    fun getChildByName(name: String): Child?
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
    fun getChildWithGroups(): List<ChildWithGroups>
}