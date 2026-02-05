package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow

interface SchedulerRepository {
   // suspend fun saveScheduler(scheduler: Scheduler)
    suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>?
    suspend fun getClientsByLesson(dayOfWeek: DayOfWeek, startTime: Int): Flow<List<Scheduler>>?
    suspend fun getClientList(name: String): List<ClientScheduler>
}