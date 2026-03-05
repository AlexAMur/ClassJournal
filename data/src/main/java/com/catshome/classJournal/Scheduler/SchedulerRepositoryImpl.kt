package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Scheduler.SchedulerRepository
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow

class SchedulerRepositoryImpl(val storage: SchedulerRoomStorage): SchedulerRepository {
    override suspend fun deleteLesson(
        dayOfWeek: DayOfWeek,
        startTime: Long
    ): Boolean {
       return storage.deleteLesson(dayOfWeek, startTime)
    }

    override suspend fun saveScheduler(dayOfWeek: DayOfWeek, startTime: Long, scheduler: List<Scheduler>) {
        storage.saveScheduler(dayOfWeek =  dayOfWeek,
            startTime = startTime,
            scheduler = scheduler.map { it.mapToSchedulerEntity()}
        )
    }

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

    override suspend fun getClientList(name:String, dayOfWeek: DayOfWeek, startTimeLesson: Int): List<ClientScheduler>{
      return  storage.getClients(name, dayOfWeek, startLesson = startTimeLesson)
    }

    override suspend fun deleteSchedule(scheduler: Scheduler): Boolean {
       return storage.delete(scheduler.mapToSchedulerEntity())
    }

    override suspend fun updateTimeLesson(
        dayOfWeek: DayOfWeek,
        oldTime: Int,
        newTime: Int,
        duration: Int
    ): Boolean {
       return storage.updateTimeLesson(
           dayOfWeek = dayOfWeek,
           timeLesson = oldTime,
           newTime = newTime,
           duration = duration
       )
    }

    //Проверка вренени для нового занятия, что-бы не было пересечения
    override fun checkTimeLesson(
        dayOfWeek: DayOfWeek,

        startTime: Int,
        duration: Int
    ): Boolean {
       return storage.checkLessonTime(dayOfWeek, startTime, duration)
    }

    override fun checkTimeLessonBeforeEditTime(
        dayOfWeek: DayOfWeek,
        oldTime: Int,
        startTime: Int,
        duration: Int
    ): Boolean {
       return storage.checkLessonTimeBeforeEdit(
           dayOfWeek = dayOfWeek,
           oldTime = oldTime,
           startTime = startTime,
           duration = duration)
    }
}