package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import kotlinx.coroutines.flow.Flow

interface ChildStorage {
        fun insert(child: Child)
        fun delete(child: Child)
       // fun update(child: Child):Boolean
        fun getChildById(uid: String):Child
        fun getChildByName(child: Child):Child
        fun read(isDelete: Boolean): Flow<List<Child>>
        fun readAll(): Flow<List<Child>>

}