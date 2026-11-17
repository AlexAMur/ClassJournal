package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    suspend fun saveChild(child: Child, childGroup: List<ChildGroup>, childDocs: List<DocChild>)
    suspend fun updateChild(child: Child)
    suspend fun deleteSet(child: Child)
   // fun checkDeleteChild(child: Child , isDelete: Boolean)
    fun getChildById(uid: String): Child?
    fun childDeleteExists(child: Child): Child?
    fun getChildByName(name: String): Flow<List<MiniChild>?>
    fun getChilds(isDelete: Boolean): Flow<List<Child>>
    fun getAllChilds(): Flow<List<Child>>
    fun getChildWithGroups(): List<ChildWithGroups>
    suspend fun getDocsByChild(child: Child):Flow<List<DocChild>>
}