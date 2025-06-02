package com.catshome.ClassJournal.domain.Group.Models

import javax.inject.Inject

data class Group (
    var uId : Int =-1,
    var name: String="",
    var isDelete: Boolean = false
)