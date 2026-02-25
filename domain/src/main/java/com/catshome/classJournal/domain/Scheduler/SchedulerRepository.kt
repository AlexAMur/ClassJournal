package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface SchedulerRepository {
    suspend fun deleteLesson(dayOfWeek: DayOfWeek, startTime: Long): Boolean
    suspend fun saveScheduler(dayOfWeek: DayOfWeek, startTime: Long,scheduler: List<Scheduler>)
    suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>?
    suspend fun getClientsByLesson(dayOfWeek: DayOfWeek, startTime: Int): Flow<List<Scheduler>>?
    suspend fun getClientList(name: String,dayOfWeek: DayOfWeek, startTimeLesson: Int):List<ClientScheduler>
    suspend fun deleteSchedule(scheduler: Scheduler): Boolean
    fun  checkTimeLesson(dayOfWeek: DayOfWeek, startTime: Int, duration: Int): Boolean
}