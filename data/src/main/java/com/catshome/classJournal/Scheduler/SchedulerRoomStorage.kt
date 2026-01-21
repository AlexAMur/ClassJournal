package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.PayList.PayDAO
import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SchedulerRoomStorage @Inject constructor(private val dao: SchedulerDAO) {
    suspend fun inset(scheduler: SchedulerEntity){
        dao.insert(scheduler)
    }
    suspend fun update(scheduler: SchedulerEntity){
        dao.update(scheduler)
    }
    suspend fun delete(scheduler: SchedulerEntity){
        dao.delete(scheduler)
    }
    suspend fun getSchedulers(): Flow<List<Scheduler>>?{
       return dao.getFull()?.map {list->
           list.map { it.mapToScheduler() }

       }
    }
}