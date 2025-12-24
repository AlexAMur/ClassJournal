package com.catshome.classJournal.communs.FilterScreen

import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.serialization.Serializable

@Serializable
data class FilterSetting (
        val childId: String? = null,
        val childFIO: String? = null,
        val optionsIndex: Int = 0,
        val sortEnum: SortEnum? = null,
        val beginDate: String? = null,
        val endDate: String? = null
)