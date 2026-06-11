package com.catshome.classJournal.navigate

import com.catshome.classJournal.communs.FilterScreen.ScreenEnum
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.serialization.Serializable

@Serializable
data class OptionFilterList(
    val childId: String? = null,
    val childFIO: String? = null,
    val sort: SortEnum? = null,
    val selectOption: Int = 1,
    val beginDate: String? = null,
    val endDate: String? = null,
    val screen: ScreenEnum? = ScreenEnum.VisitListScreen
)
