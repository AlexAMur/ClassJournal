package com.catshome.classJournal.domain.Child

import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    fun saveChild(child: Child, childGroup: List<ChildGroup>)
    fun updateChild(child: Child, childGroup: List<ChildGroup>)
    fun setDelete(child: Child?)  //пометить на удаление
    fun deleteChild(child: Child)// удалить полностью
    fun checkDeleteChild(child: Child , isDelete: Boolean)
    fun getChildById(uid: String): Child?
    fun getChildByName(name: String): Child?
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
    fun getChildWithGroups(): List<ChildWithGroups>
}