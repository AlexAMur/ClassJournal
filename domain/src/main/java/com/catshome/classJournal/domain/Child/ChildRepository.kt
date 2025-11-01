package com.catshome.classJournal.domain.Child

import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    suspend fun saveChild(child: Child, childGroup: List<ChildGroup>)
    suspend fun updateChild(child: Child, childGroup: List<ChildGroup>)
    suspend fun setDelete(child: Child?)  //пометить на удаление
    suspend fun deleteChild(child: Child)// удалить полностью
    fun checkDeleteChild(child: Child , isDelete: Boolean)
    fun getChildById(uid: String): Child?
    fun childExists(child: Child): Child?
    fun getChildByName(name: String): Child?
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
    fun getChildWithGroups(): List<ChildWithGroups>
}