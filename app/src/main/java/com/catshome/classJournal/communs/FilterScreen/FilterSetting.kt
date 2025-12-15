package com.catshome.classJournal.communs.FilterScreen

import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Date
@Serializable
data class FilterSetting (
        val childId: String? = null,
        val childFIO: String? = null,
        val optionsIndex: Int = 0,
        val beginDate: String? = null,
        val endDate: String? = null
)