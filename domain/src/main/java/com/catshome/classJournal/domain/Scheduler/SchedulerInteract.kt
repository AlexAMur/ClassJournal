package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import javax.inject.Inject

class SchedulerInteract @Inject constructor(private val repository: SchedulerRepository) {
    suspend fun save() {
    //TODO здесь будет сохраняться списко участников занятия
    //    repository.getScheduler()
    }
    suspend fun editTime(dayOfWeek: DayOfWeek, oldTime:Int, newTime: Int, duration: Int) {
            //TODO здесь  будет измение времени занятия
    }

    suspend fun getScheduler(): Flow<List<Scheduler>>? {
        return  repository.getScheduler()
    }
    suspend fun getListClient(name: String): List<ClientScheduler>{
        return repository.getClientList(name)
    }
}