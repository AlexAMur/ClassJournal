package com.catshome.classJournal.domain.Scheduler

import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import javax.inject.Inject

class SchedulerInteract @Inject constructor(private val repository: SchedulerRepository) {
    suspend fun saveScheduler(dayOfWeek: DayOfWeek, time: Long, duration: Int, list: List<ClientScheduler>) {
        repository.saveScheduler(dayOfWeek= dayOfWeek,
            startTime = time,
            list.map {
                it.mapToScheduler(dayOfWeek = dayOfWeek,
                    startLesson = time,
                    duration = duration)
            })
    }
    suspend fun editTime(dayOfWeek: DayOfWeek, oldTime:Int, newTime: Int, duration: Int) {
            //TODO здесь  будет измение времени занятия
    }

    //Возвращает список клиентов в расписании весь или за опред. день(DayOfWeek)
    suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>? {
        return repository.getScheduler(dayOfWeek)
    }
    suspend fun getClientsSchedulerLesson (dayOfWeek: DayOfWeek, startTime: Int): Flow<List<Scheduler>>? {
        return repository.getClientsByLesson(dayOfWeek, startTime)
    }
    suspend fun getListClient(name: String):List<ClientScheduler>{
        return repository.getClientList(name)
    }
}