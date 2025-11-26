package com.catshome.classJournal.child

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val childStorage: ChildStorage) : ChildRepository {
    val TAG = "CLJR"
    override suspend fun saveChild(child: Child, childGroup: List<ChildGroup>) {
        if (childStorage.getChildById(child.uid) == null)
            childStorage.insert(child, childGroup)
        else{

            childStorage.updateChildWithGroups(child = child, childGroup)
        }
    }

    override suspend fun updateChild(child: Child) {
        try {
            childStorage.update(child)
        } catch (e: SQLiteConstraintException) {
            Log.e(TAG, "Error SQL Constraint Exception ${e.message}")
        }
    }

    override suspend fun deleteSet(child: Child) {
       childStorage.deleteSet(child)
    }

//
//    override suspend fun deleteChild(child: Child) {
//        childStorage.delete(child)
//    }

//    override fun checkDeleteChild(
//        child: Child,
//        isDelete: Boolean
//    ) {
//        TODO("Not yet implemented")
//    }

    override fun getChildById(uid: String): Child? {
        return childStorage.getChildById(uid)
    }
//проверяет есть ли удаленные
    override fun childDeleteExists(child: Child): Child? {
        return childStorage.childDeleteExists(child.mapToChildEntity())?.mapToChild()
    }

    override fun getChildByName(name: String): Flow<List<MiniChild>?> {
        return childStorage.getChildByName(name = name)
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