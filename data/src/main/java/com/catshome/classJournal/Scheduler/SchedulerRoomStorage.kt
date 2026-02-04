package com.catshome.classJournal.Scheduler

import com.catshome.classJournal.PayList.PayDAO
import com.catshome.classJournal.child.ChildGroupDAO
import com.catshome.classJournal.domain.Scheduler.ClientScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.CoroutineScope
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

   suspend fun getClients(name: String): List<ClientScheduler> {

        val clients = daoClient.getGroupsByName(name).map {
            ClientScheduler(
                uidChild = null,
                uidGroup = it.uid,
                name = it.name,
                isChecked = false
            )
        }.sortedBy { name }.toMutableList()
        clients.addAll(
            daoClient.getChildByName(name).map {
                ClientScheduler(
                    uidChild = it.uid,
                    uidGroup = null,
                    name = it.name,
                    isChecked = false
                )
            }.sortedBy { name })

        return clients.toList()
    }
}