package com.catshome.classJournal.domain.Child

import java.sql.Date
import java.util.UUID

data class Child(
    var uid : String = "",
    var name: String = "",
    var surname: String ="",
    var birthday: String = "",
    var phone: String = "",
    var note: String = "",
    val saldo: Int = 0,
    var isDelete: Boolean = false

)
