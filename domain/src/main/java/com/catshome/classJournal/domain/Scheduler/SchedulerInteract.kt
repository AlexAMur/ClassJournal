package com.catshome.classJournal.domain.Scheduler

import android.util.Log
import com.android.identity.util.UUID
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
                it.mapToScheduler(
                    dayOfWeek = dayOfWeek,
                    startLesson = time,
                    duration = duration,
                    )
            })
    }
    suspend fun checkTimeLesson(dayOfWeek: DayOfWeek,oldTime: Int?,  startTime: Int, duration: Int): Boolean {
        return if (oldTime != null){
            Log.e ("ClJR", "time before")
            repository.checkTimeLessonBeforeEditTime(dayOfWeek = dayOfWeek,
                oldTime = oldTime,
                startTime = startTime,
                duration = duration
            )
        }else{
            Log.e ("ClJR", "time check")
            repository.checkTimeLesson(dayOfWeek = dayOfWeek,
                startTime = startTime,
                duration = duration
            )
        }
    }
    suspend fun editTime(dayOfWeek: DayOfWeek, oldTime:Int, newTime: Int, duration: Int) {
           repository.updateTimeLesson(
               dayOfWeek =  dayOfWeek,
               oldTime = oldTime,
               newTime = newTime,
               duration = duration
           )
    }
    suspend fun deleteLesson(dayOfWeek: DayOfWeek, time: Long){
        repository.deleteLesson(dayOfWeek = dayOfWeek, startTime = time)
    }

    //Возвращает список клиентов в расписании весь или за опред. день(DayOfWeek)
    suspend fun getScheduler(dayOfWeek: DayOfWeek?): Flow<List<Scheduler>>? {
        return repository.getScheduler(dayOfWeek)
    }
    suspend fun getClientsSchedulerLesson (dayOfWeek: DayOfWeek, startTime: Int): Flow<List<Scheduler>>? {
        return repository.getClientsByLesson(dayOfWeek, startTime)
    }
    suspend fun getListClient(name: String, dayOfWeek: DayOfWeek, startTimeLesson: Int):List<ClientScheduler>{
        return repository.getClientList(name, dayOfWeek,startTimeLesson)
    }
    suspend fun deleteClient(scheduler: Scheduler): Boolean{
       return repository.deleteSchedule(scheduler)
    }
}