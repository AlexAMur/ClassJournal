package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildWithGroups
import kotlinx.coroutines.flow.Flow

interface ChildStorage {
        suspend fun insert(child: Child, childGroup: List<ChildGroup>)
        suspend fun delete(child: Child)
        suspend fun deleteSet(child: Child)
        suspend fun updateChildWithGroups(child: Child, childGroup: List<ChildGroup>)
        suspend fun update(child: Child)
        fun getChildById(uid: String):Child?
        fun getChildByName(child: Child): Flow<List<Child>>
        fun read(isDelete: Boolean): Flow<List<Child>>
        fun readAll(): Flow<List<Child>>
        fun getChildWithGroups(isDelete: Boolean =false): List<ChildWithGroups>
        fun childDeleteExists(child: ChildEntity): ChildEntity?

}