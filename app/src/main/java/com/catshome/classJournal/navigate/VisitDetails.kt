package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class VisitDetails(
    val uid: String ="",
    val uidChild: String?= null,
    val fio: String? =null,
    val date: String? = null,
    val price: Int=0

)
