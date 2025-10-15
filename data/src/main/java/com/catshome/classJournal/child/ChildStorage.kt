package com.catshome.classJournal.child

import android.content.Context
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildWithGroups
import kotlinx.coroutines.flow.Flow

interface ChildStorage {
        fun insert(child: Child, childGroup: List<ChildGroup>)
        fun delete(child: Child)
        fun update(child: Child, childGroup: List<ChildGroup>)
        fun getChildById(uid: String):Child
        fun getChildByName(child: Child): Flow<List<Child>>
        fun read(isDelete: Boolean): Flow<List<Child>>
        fun readAll(): Flow<List<Child>>
        fun getChildWithGroups(isDelete: Boolean =false): List<ChildWithGroups>

}