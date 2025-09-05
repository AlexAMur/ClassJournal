package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val childStorage: ChildStorage):ChildRepository {
    override fun saveChild(child: Child) {
       childStorage.insert(child)
    }


    override fun deleteChild(child: Child) {
        childStorage.delete(child)
    }

    override fun getChildById(uid: String): Child {
        childStorage.getChildById(uid)
    }

    override fun getChildByName(name: String): Child {
        TODO("Not yet implemented")
    }

    override fun getChilds(isDelete: Boolean): Flow<List<Child>> {
        TODO("Not yet implemented")
    }

    override fun getAllChilds(): Flow<List<Child>> {
        TODO("Not yet implemented")
    }
}