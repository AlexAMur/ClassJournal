package com.catshome.classJournal.child.DocChild

import android.content.Context
import androidx.core.net.toUri
import com.catshome.classJournal.child.ChildDAO
import com.catshome.classJournal.child.ChildGroupDAO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DocChildStorage @Inject constructor(
    @ApplicationContext val context: Context,
    val childDAO: ChildDAO,
    val docsDAO: ChildDocFileDAO
) {
    fun deleteDocByID(id: Long) {
        docsDAO.selectDocById(id).let { doc ->

            docsDAO.delete(doc)
        }
    }
}