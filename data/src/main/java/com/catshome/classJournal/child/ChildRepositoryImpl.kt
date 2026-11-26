package com.catshome.classJournal.child

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.core.net.toUri
import com.catshome.classJournal.child.DocChild.DocChildStorage
import com.catshome.classJournal.docFiles.DocFileSaver
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import com.catshome.classJournal.domain.Child.MiniChild
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(@ApplicationContext val context: Context,
                                              val childStorage: ChildStorage, val docChildStorage: DocChildStorage) : ChildRepository {
    val TAG = "CLJR"
    override suspend fun saveChild(child: Child,
                                   childGroup: List<ChildGroup>,
                                   childDocs: List<DocChild>
    ) {
        // новые документы с пометкой удалить не нужны
        val docs = childDocs.filter{ !(it.isNew && it.isDelete)}
        docs.filter { it.isDelete ==true }.let{  deleteDoc->
                deleteDoc.forEach { Log.e("CLJR", "${it.fileName} ${it.isDelete}") }
          docChildStorage.deleteDocs(listDocs = deleteDoc) //удаляем помеченные на удаление
        }
        if (childStorage.getChildById(child.uid) == null)
            childStorage.insert(child, childGroup, docs.filter { it.isDelete == false })
        else{
            childStorage.updateChildWithGroups(child = child, childGroup, docs.filter { it.isDelete == false })
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