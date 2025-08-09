package com.catshome.classJournal.screens.child

data class NewChildState(
    var uid: Long = 0L,
    var name: String = "",
    var surname: String = "",
    var phone: String = "",
    var note: String = "",
    var birthday: String = "01.01.1990"
)
