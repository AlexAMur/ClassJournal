package com.catshome.classJournal.PayList

import com.catshome.classJournal.Visit.VisitScreenEntity
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.VisitSortEnum

fun getSortString(sortEnum: SortEnum): String{

    when(sortEnum){
        SortEnum.date_pay -> return PayScreenEntity::date_pay.name
        SortEnum.Surname ->return PayScreenEntity::Surname.name
    }
}
fun getSortString(sortEnum: VisitSortEnum): String{

    when(sortEnum){
        VisitSortEnum.dateVisit -> return VisitScreenEntity::dateVisit.name
        VisitSortEnum.Surname -> return VisitScreenEntity::Surname.name
    }
}