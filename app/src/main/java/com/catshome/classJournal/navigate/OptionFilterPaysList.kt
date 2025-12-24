package com.catshome.classJournal.navigate

import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.serialization.Serializable

@Serializable
data class OptionFilterPaysList(
    val childId: String? = null,
    val childFIO: String? = null,
    val sort: SortEnum? = null,
    val selectOption: Int = 0,
    val beginDate: String? = null,
    val endDate: String? = null
)
