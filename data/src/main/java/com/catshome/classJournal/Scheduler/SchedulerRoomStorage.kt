package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.communs.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SchedulerRoomStorage @Inject constructor(
    private val dao: SchedulerDAO,
    private val daoClient: ChildGroupDAO
) {
    suspend fun inset(scheduler: SchedulerEntity) {
        dao.insert(scheduler)
    }

    suspend fun update(scheduler: SchedulerEntity) {
        dao.update(scheduler)
    }

    suspend fun delete(scheduler: SchedulerEntity) {
        dao.delete(scheduler)
    }

    suspend fun getSchedulers(): Flow<List<Scheduler>>? {
        return dao.getFull()?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    suspend fun getSchedulersByDay(dayOfWeek: Int): Flow<List<Scheduler>>? {
        return dao.getSchedulerByDay(dayOfWeek)?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    suspend fun getClientByLesson(dayOfWeek: Int, startTime: Long): Flow<List<Scheduler>>? {
        return dao.getClientsSchedulerByLesson(dayOfWeek, startTime)?.map { list ->
            list.map { it.mapToScheduler() }
        }
    }
    // отбор клиентов и/или групп по имени
    suspend fun getClients(name: String): Flow<List<ClientScheduler>> {
        val clients = daoClient.getGroupsByName(name).map {
            it.map {
            ClientScheduler(
                uidChild = null,
                uidGroup = it.uid,
                name = it.name,
                isChecked = false
            )
        }.sortedBy { name }.toMutableList()}
        clients.collect{list->
            daoClient.getChildByName(name).map{miniChild->
                miniChild.map {
                    list.add( ClientScheduler(
                    uidChild = it.uid,
                    uidGroup = null,
                    name = it.name,
                    isChecked = false
                ))
            }.sortedBy { name }
            }}
        return clients
    }
}