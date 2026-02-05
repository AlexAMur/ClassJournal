package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Scheduler.SchedulerRepository
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow

class SchedulerRepositoryImpl(val storage: SchedulerRoomStorage): SchedulerRepository {
    override suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>? {
        dayOfWeek?.let {
            return storage.getSchedulersByDay(it.ordinal)
        }
        return storage.getSchedulers()
    }

    override suspend fun getClientsByLesson(
        dayOfWeek: DayOfWeek,
        startTime: Int
    ): Flow<List<Scheduler>>? {
      return storage.getClientByLesson(dayOfWeek.ordinal, startTime.toLong())
    }

    override suspend fun getClientList(name:String): List<ClientScheduler> {
      return  storage.getClients(name)
    }
}