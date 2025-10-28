package com.catshome.classJournal.child

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.ChildWithGroups
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val childStorage: ChildStorage) : ChildRepository {
    val TAG = "CLJR"
    override fun saveChild(child: Child, childGroup: List<ChildGroup>) {
        try {
            if (childStorage.getChildById(child.uid) == null)
                childStorage.insert(child, childGroup)
            else
                childStorage.update(child = child, childGroup)
        } catch (e: SQLiteConstraintException) {
            Log.e(TAG, "Error SQL Constraint Exception ${e.message}")
        }
    }

    override fun updateChild(child: Child, childGroup: List<ChildGroup>) {
        try {
            childStorage.update(child, childGroup)
        } catch (e: SQLiteConstraintException) {
            Log.e(TAG, "Error SQL Constraint Exception ${e.message}")
        }
    }

    override fun setDelete(child: Child?) {
        if (child != null)
            updateChild(child, emptyList())
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

    override fun getChildById(uid: String): Child? {
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