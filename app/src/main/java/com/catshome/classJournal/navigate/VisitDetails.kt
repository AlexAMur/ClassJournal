package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class VisitDetails(
    val uid: String ="",
    val uidChild: String="",
    val fio: String="",
    val date: String="",
    val price: Int=0

)
