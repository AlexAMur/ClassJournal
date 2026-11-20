package com.catshome.classJournal.screens.child

import android.net.Uri
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group

sealed class NewChildEvent {

    class OpenChild(val uid: String): NewChildEvent()
    class OpenFile(val uri: String): NewChildEvent()
    class ShareFile(val uri: String): NewChildEvent()
    class onChangeDocNoteFile(val Index: Int,val text: String): NewChildEvent()
    class SaveChild(val child: Child): NewChildEvent()
    data object SaveClicked: NewChildEvent()
    data object ReloadScreen: NewChildEvent()
    data object CloseClicked: NewChildEvent()
    data object newChild: NewChildEvent()
    class noClearNote(val index: Int): NewChildEvent()
    class SelectGroup(val uidGroup: String ): NewChildEvent()
    class SelectDocs(val listUri: List<Uri?>): NewChildEvent()
}