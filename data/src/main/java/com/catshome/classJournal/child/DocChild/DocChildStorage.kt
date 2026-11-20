package com.catshome.classJournal.child.DocChild

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.room.Transaction
import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.docFiles.DocFileSaver
import com.catshome.classJournal.docFiles.deleteFileByUri
import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocChildStorage @Inject constructor(
    @ApplicationContext val context: Context,
    val childDAO: ChildDAO,
    val docsDAO: ChildDocFileDAO
) {
    @Transaction
    fun insertDocs(listDocs: List<DocChild>) {
        Log.e("CLJR", "insert doc")
        listDocs.map { doc ->
            if (doc.isNew) {
                DocFileSaver.saveFileToInternalStorage(
                    context,
                    doc.fileUri.toUri()
                )?.let { doc.copy(fileUri = it) }
            }
            else doc
        }.let {
            it.forEach { doc ->
                doc?.let {
                    Log.e("CLJR", "${it.fileUri}")
                    docsDAO.insert(doc.mapToChildDocFileEntity())
                }
            }
        }

    }

    @Transaction
    fun deleteDocs(listDocs: List<DocChild>): Boolean {
        try {
            listDocs.forEach {
                if (it.isDelete) context.deleteFileByUri(it.fileUri.toUri())
                deleteDocByID(it.id)
            }
            return true
        } catch (e: Exception) {
            Log.e("CLJR", e.message.toString())
            return false
        }
    }

    fun docsByChildID(childID: String): Flow<List<DocChild>> {
        return docsDAO.docsByChileID(childID).map { list -> list.map { it.mapToDocChild() } }
    }

    fun deleteDocByID(id: Long) {
        docsDAO.selectDocById(id).let { doc ->

            docsDAO.delete(doc)
        }
    }
}