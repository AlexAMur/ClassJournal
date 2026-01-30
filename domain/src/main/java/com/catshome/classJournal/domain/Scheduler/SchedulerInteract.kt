package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchedulerInteract @Inject constructor(private val repository: SchedulerRepository) {

    suspend fun save() {
        repository.getScheduler()
    }

    suspend fun getScheduler(): Flow<List<Scheduler>>? {
        return  repository.getScheduler()
    }
}
//val spisoc = listOf(
//    Scheduler(
//        uid = "1",
//        dayOfWeek = "Пн",
//        dayOfWeekInt = 0,
//        uidChild = "1",
//        uidGroup = null,
//        name = "Саша",
//        startLesson = 1,
//        duration = 40
//    ),
//    Scheduler(
//        uid = "1",
//        dayOfWeek = "Пн",
//        dayOfWeekInt = 0,
//        uidChild = "2",
//        uidGroup = null,
//        name = "Дима",
//        startLesson = 1,
//        duration = 40
//    ),
//    Scheduler(
//        uid = "1",
//        dayOfWeek = "Пн",
//        dayOfWeekInt = 0,
//        uidChild = "1",
//        uidGroup = null,
//        name = "Саша",
//        startLesson = 2,
//        duration = 40
//    ),
//    Scheduler(
//        uid = "1",
//        dayOfWeek = "Пт",
//        dayOfWeekInt = 5,
//        uidChild = "3",
//        uidGroup = null,
//        name = "Миша",
//        startLesson = 1,
//        duration = 40
//    )
//)
