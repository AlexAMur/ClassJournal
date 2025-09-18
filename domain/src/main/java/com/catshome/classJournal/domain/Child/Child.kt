package com.catshome.classJournal.domain.Child

import java.sql.Date
import java.util.UUID

data class Child(
    var uid : String = UUID.randomUUID().toString(),
    var name: String = "",
    var surname: String ="",
    var birthday: String = "",
    var phone: String = "",
    var note: String = "",
    var isDelete: Boolean = false

)
