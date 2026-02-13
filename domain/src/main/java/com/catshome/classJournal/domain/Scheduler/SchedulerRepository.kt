package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow

interface SchedulerRepository {
    suspend fun deleteLesson(dayOfWeek: DayOfWeek, startTime: Long): Boolean
    suspend fun saveScheduler(dayOfWeek: DayOfWeek, startTime: Long,scheduler: List<Scheduler>)
    suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>?
    suspend fun getClientsByLesson(dayOfWeek: DayOfWeek, startTime: Int): Flow<List<Scheduler>>?
    suspend fun getClientList(name: String):List<ClientScheduler>
    suspend fun deleteSchedule(scheduler: Scheduler): Boolean
}