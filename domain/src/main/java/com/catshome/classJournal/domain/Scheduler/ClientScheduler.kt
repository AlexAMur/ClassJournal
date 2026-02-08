package com.catshome.classJournal.domain.Scheduler

import com.android.identity.util.UUID
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlin.time.Duration

data class ClientScheduler(
    val uid: String = UUID.randomUUID().toString(),
    val uidChild: String? = null,
    val uidGroup: String? = null,
    val name: String,
    val isChecked: Boolean = false,
)

fun ClientScheduler.mapToScheduler(
    dayOfWeek: DayOfWeek,
    startLesson: Long,
    duration: Int
): Scheduler {
    return Scheduler(
        uid = this.uid,
        dayOfWeek = dayOfWeek.shortName,
        dayOfWeekInt = dayOfWeek.ordinal,
        uidChild = this.uidChild,
        uidGroup = this.uidGroup,
        name = this.name,
        startLesson = startLesson,
        duration = duration
    )
}
