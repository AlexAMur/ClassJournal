package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchedulerInteract @Inject constructor(private val repository: SchedulerRepository) {
    suspend fun save() {
    //    repository.getScheduler()
    }
    suspend fun getScheduler(): Flow<List<Scheduler>>? {
        return  repository.getScheduler()
    }
    suspend fun getListClient(name: String): List<ClientScheduler>{
        return repository.getClientList(name)
    }
}