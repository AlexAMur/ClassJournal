package com.catshome.classJournal.domain.Scheduler

import kotlinx.coroutines.flow.Flow

interface SchedulerRepository {
   // suspend fun saveScheduler(scheduler: Scheduler)
    suspend fun getScheduler(): Flow<List<Scheduler>>?
    suspend fun getClientList(): Flow<List<ClientScheduler>>
}