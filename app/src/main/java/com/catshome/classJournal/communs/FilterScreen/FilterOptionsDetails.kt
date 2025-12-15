package com.catshome.classJournal.communs.FilterScreen

import kotlinx.serialization.Serializable

@Serializable
data class FilterOptionsDetails(
    val childId: String? = null,
    val childFIO: String? = null,
    val selectedIndex: Int  = 0,
    val beginDate: String?,
    val endDate: String?,
)