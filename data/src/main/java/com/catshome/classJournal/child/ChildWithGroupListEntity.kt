package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.communs.toDateStringRU

data class ChildWithGroupListEntity(
    val childUid : String,
    val childName: String,
    val childSurname: String,
    val childBirthday: String,
    val groupUid: String?,
    val groupName: String?
)
fun ChildWithGroupListEntity.mapToChildWithGroups(): ChildWithGroups{
   return ChildWithGroups(
       childUid = this.childUid,
     childName = this.childName,
     childSurname = this.childSurname,
     childBirthDay = this.childBirthday.toLong().toDateStringRU(),
     groupUid = this.groupUid?:"",
     groupName = this.groupName?:"Без группы"
   )
}