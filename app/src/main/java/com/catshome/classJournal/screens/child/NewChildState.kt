package com.catshome.classJournal.screens.child

import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Group.Models.Group

data class ItemScreenChildGroup(
    val group: Group? ,
    var isChecked: Boolean =false
)
data class NewChildState(
    var outerNavigation: NavHostController? = null,
    var child: Child =Child(),
    var listScreenChildGroup: List<ItemScreenChildGroup>? = emptyList(),
    var indexGroup: Int = -1,
    var indexFocus:Int = -1,
    var isNameError: Boolean= false,
    var isSurnameError: Boolean= false,
    var isError: Boolean= false,
    var errorMessage: String = "",
)
//{
//    fun copy(child: Child): NewChildState{
//
//        return NewChildState(
//             child =  child,
//
//            listScreenChildGroup = this.listScreenChildGroup,
//            indexGroup = this.indexGroup,
//            indexFocus = this.indexFocus
//        )

//    }



//    fun copy(
//        child: Child =this.child,
//        listScreenChildGroup: List<ItemScreenChildGroup>? = this.listScreenChildGroup
//    ): NewChildState{
//
//        return NewChildState(
//            child =  child,
//            listScreenChildGroup = listScreenChildGroup,
//            indexGroup = this.indexGroup,
//            indexFocus = this.indexFocus
//        )
//
//    }
//    fun copy(listScreenChildGroup: List<ItemScreenChildGroup>?): NewChildState{
//        return NewChildState(
//            child = this.child,
//            listScreenChildGroup = listScreenChildGroup,
//            indexGroup = this.indexGroup,
//            indexFocus = this.indexFocus
//        )
//    }
//}
