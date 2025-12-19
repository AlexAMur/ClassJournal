package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class OptionFilterPaysList(
    val childId: String? = null,
    val childFIO: String? = null,
    val selectOption: Int = 0,
    val beginDate: String? = null,
    val endDate: String? = null
)
