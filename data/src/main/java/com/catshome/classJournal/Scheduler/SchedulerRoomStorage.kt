package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class SchedulerRoomStorage @Inject constructor(
    private val daoScheduler: SchedulerDAO,
    private val daoClient: ChildGroupDAO
) {
    suspend fun deleteLesson(dayOfWeek: DayOfWeek, time: Long): Boolean{
       return daoScheduler.deleteLesson(dayOfWeek.ordinal, time)
    }
    suspend fun saveScheduler(dayOfWeek: DayOfWeek, startTime: Long, scheduler: List<SchedulerEntity>) {
        daoScheduler.saveScheduler(dayOfWeek = dayOfWeek.ordinal,
            startLesson = startTime,
            scheduler)
    }

    suspend fun update(scheduler: SchedulerEntity) {
        daoScheduler.update(scheduler)
    }

    suspend fun delete(scheduler: SchedulerEntity): Boolean {
        if (daoScheduler.delete(scheduler)>0)
            return true
        else
            return false
    }

    suspend fun getSchedulers(): Flow<List<Scheduler>>? {
        return daoScheduler.getFull()?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    suspend fun getSchedulersByDay(dayOfWeek: Int): Flow<List<Scheduler>>? {
        return daoScheduler.getSchedulerByDay(dayOfWeek)?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    suspend fun getClientByLesson(dayOfWeek: Int, startTime: Long): Flow<List<Scheduler>>? {
        return daoScheduler.getClientsSchedulerByLesson(dayOfWeek, startTime)?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    // отбор клиентов и/или групп по имени
    suspend fun getClients(name: String): List<ClientScheduler> {
        val clients = daoClient.getGroupsByName(name).map {
            ClientScheduler(
                uidChild = null,
                uidGroup = it.uid,
                name = it.name,
                isChecked = false
            )
        }.sortedBy { name }.toMutableList()
        clients.addAll(daoClient.getChildByName(name).map{miniChild->
                    ClientScheduler(
                        uidChild = miniChild.uid,
                        uidGroup = null,
                        name = miniChild.name,
                        isChecked = false
                    )
            }.sortedBy { name })

        return clients.toList()
    }
}