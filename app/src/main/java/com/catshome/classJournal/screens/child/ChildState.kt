package com.catshome.classJournal.screens.child

import androidx.navigation.NavHostController
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.group.NewGroupState

data class NewChildState(

    var outerNavigation: NavHostController? = null,
    var child: Child =Child(),
//    var uid: String= "",
//    var name: String = "",
//    var surname: String = "",
//    var phone: String = "",
//    var note: String = "",
//    var birthday: String = "01.01.1990",
//    var isDelete: Boolean = false,
    var indexFocus:Int = -1
){
    fun copy(child: Child): NewChildState{

        return NewChildState(
            child =  child,
            indexFocus = this.indexFocus
        )
    }
}
