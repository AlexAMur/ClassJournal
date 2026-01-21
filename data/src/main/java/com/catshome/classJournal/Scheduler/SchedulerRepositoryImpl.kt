package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Scheduler.SchedulerRepository
import kotlinx.coroutines.flow.Flow

class SchedulerRepositoryImpl(val storage: SchedulerRoomStorage): SchedulerRepository {
    override suspend fun getScheduler(): Flow<List<Scheduler>>? {
       return storage.getSchedulers()
    }
}