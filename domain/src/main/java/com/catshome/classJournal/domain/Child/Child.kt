package com.catshome.classJournal.domain.Child

import java.sql.Date
import java.util.UUID

data class Child(
    var uid : String = UUID.randomUUID().toString(),
    var name: String?=null,
    var surname: String? = null,
    var birthday: String? = null,
    var phone: String? = null,
    var note: String? = null,
    var isDelete: Boolean = false

)
