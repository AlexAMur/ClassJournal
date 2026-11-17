package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow

interface ChildStorage {
    suspend fun insert(child: Child, childGroup: List<ChildGroup>, childDocs: List<DocChild>)
    suspend fun selectDocsChild(child: Child): Flow<List<DocChild>>
    suspend fun delete(child: Child)
    suspend fun deleteSet(child: Child)
    suspend fun updateChildWithGroups(
        child: Child,
        childGroup: List<ChildGroup>,
        childDocs: List<DocChild>
    )

    suspend fun update(child: Child)
    fun getChildById(uid: String): Child?
    fun getChildByName(name: String): Flow<List<MiniChild>>
    fun read(isDelete: Boolean): Flow<List<Child>>
    fun readAll(): Flow<List<Child>>
    fun getChildWithGroups(isDelete: Boolean = false): List<ChildWithGroups>
    fun childDeleteExists(child: ChildEntity): ChildEntity?

}