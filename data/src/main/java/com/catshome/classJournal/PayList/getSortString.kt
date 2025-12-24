package com.catshome.classJournal.PayList

import com.catshome.classJournal.domain.communs.SortEnum

fun getSortString(sortEnum: SortEnum): String{

    when(sortEnum){
        SortEnum.date_pay -> return PayScreenEntity::date_pay.name
        SortEnum.Surname ->return PayScreenEntity::Surname.name
    }
}