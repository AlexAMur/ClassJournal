package com.catshome.classJournal.child

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.core.net.toUri
import com.catshome.classJournal.child.DocChild.DocChildStorage
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(val childStorage: ChildStorage,val docChildStorage: DocChildStorage) : ChildRepository {
    val TAG = "CLJR"
    override suspend fun saveChild(child: Child,
                                   childGroup: List<ChildGroup>,
                                   childDocs: List<DocChild>
    ) {
        childDocs.forEach { if (it.isDelete)

                    docChildStorage.deleteDocByID(it.id)
        }
        if (childStorage.getChildById(child.uid) == null)
            childStorage.insert(child, childGroup, childDocs)
        else{
            childStorage.updateChildWithGroups(child = child, childGroup, childDocs)
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
    override suspend fun getDocsByChild(child: Child):Flow<List<DocChild>> {
        return childStorage.selectDocsChild(child)
    }

}