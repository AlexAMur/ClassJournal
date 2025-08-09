package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val roomGroupStorage: ChildStorage):ChildRepository {
    override fun saveChild(child: Child) {
        TODO("Not yet implemented")
    }

    override fun updateChild(child: Child): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteChild(child: Child) {
        TODO("Not yet implemented")
    }

    override fun getChildById(uid: Long): Child {
        TODO("Not yet implemented")
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