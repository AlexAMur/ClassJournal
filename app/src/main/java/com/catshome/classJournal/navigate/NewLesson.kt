package com.catshome.classJournal.navigate

import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class NewLesson(
    val dayOfWeek: DayOfWeek,
    val startTime: Int,
    val duration: Int
)
