package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.ChildWithGroups
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val childStorage: ChildStorage):ChildRepository {
    override fun saveChild(child: Child, childGroup: List<ChildGroup>) {
       childStorage.insert(child,childGroup)
    }

    override fun updateChild(child: Child, childGroup: List<ChildGroup>) {
        childStorage.update(child, childGroup)
    }


    override fun deleteChild(child: Child) {
        childStorage.delete(child)
    }

    override fun checkDeleteChild(
        child: Child,
        isDelete: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun getChildById(uid: String): Child {
      return childStorage.getChildById(uid)

    }

    override fun getChildByName(name: String): Child {
        TODO("Not yet implemented")
    }

    override fun getChilds(isDelete: Boolean): Flow<List<Child>> {
      return childStorage.read(isDelete)
    }

    override fun getAllChilds(): Flow<List<Child>> {
       return childStorage.readAll()
    }

    override fun getChildWithGroups(): List<ChildWithGroups> {
        return childStorage.getChildWithGroups(false)
    }

}