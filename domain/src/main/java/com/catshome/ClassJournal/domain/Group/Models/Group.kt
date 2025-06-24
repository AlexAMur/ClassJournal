package com.catshome.ClassJournal.domain.Group.Models

import java.util.UUID
import javax.inject.Inject

data class Group (
    var uid : Long = 0,
    var name: String="",
    var isDelete: Boolean = false
)