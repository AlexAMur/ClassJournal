package com.catshome.classJournal.domain.Group.Models

import java.util.UUID

data class Group (
    var uid : String = "",
    var name: String="",
    var isDelete: Boolean = false
)