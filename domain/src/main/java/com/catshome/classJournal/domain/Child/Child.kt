package com.catshome.classJournal.domain.Child

import java.sql.Date

data class Child(
         var uid : Long = 0,
        var name: String?=null,
        var surname: String? = null,
        var birthday: Date? = null,
        var sex: Boolean? = null,
        var isDelete: Boolean = false

    )
