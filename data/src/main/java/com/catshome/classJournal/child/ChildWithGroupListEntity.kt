package com.catshome.classJournal.child

import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu


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
     childBirthDay = this.childBirthday.toLong().toLocalDateTimeRu()?.toDateTimeRuString().toString(),
     groupUid = this.groupUid?:"",
     groupName = this.groupName?:"Без группы"
   )
}