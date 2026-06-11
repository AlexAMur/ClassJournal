package com.catshome.classJournal.Visit

import com.catshome.classJournal.Scheduler.SchedulerDAO
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VisitRoomStorage @Inject constructor(val visitDAO: VisitDAO, val schedulerDAO: SchedulerDAO) {
    suspend fun getVisitByUid(uid: String): Visit? {
        return visitDAO.getVisitByUid(uid)?.mapToVisit()
    }

    suspend fun getVisitByChildPeriod(
        uidChild: String,
        begin: Long,
        end: Long
    ): Flow<List<Visit>>? {
        return visitDAO.getVisitByChildIDWithPeriod(
            uidChild = uidChild,
            begin = begin,
            end = end

        )?.map { list -> list.map { it.mapToVisit() } }
    }
    suspend fun save(visit: List<VisitEntity>): Boolean {
        return visitDAO.addVisit(visit)
    }
    suspend fun update(visit: List<VisitEntity>): Boolean {
        return visitDAO.updateVisit(visit)
    }

    suspend fun delete(visitEntity: List<VisitEntity>): Boolean {
        return visitDAO.deleteVisit(visitEntity)
    }

    fun getVisitAll(sortEnum: SortEnum): Flow<List<Visit>>? {
        return visitDAO.getFullVisit(
            sortDate = if (sortEnum.name == SortEnum.Date.name)
                VisitScreenEntity::dateVisit.name else "",
            sortSurname = if (sortEnum.name == SortEnum.FIO.name)
                VisitScreenEntity::Surname.name else "",
        )?.map { list -> list.map { it.mapToVisit() } }
    }

    fun getVisitByPeriod(begin: Long, end: Long, sortEnum: SortEnum): Flow<List<Visit>>? {
        return visitDAO.getVisitByPeriod(
            begin = begin,
            end = end,
            sortDate = if (sortEnum.name == SortEnum.Date.name)
                VisitScreenEntity::dateVisit.name else "",
            sortSurname = if (sortEnum.name == SortEnum.FIO.name)
                VisitScreenEntity::Surname.name else ""
        )?.map { list -> list.map { it.mapToVisit() } }
    }

    //расписание на один день
    fun getScheduler(dayOfWeek: DayOfWeek): Flow<List<Visit>>? {
        return visitDAO.getListClientScheduler(dayOfWeek = dayOfWeek.ordinal)?.map{list->
            list.map { it.copy(priceScreen = it.price.toString()) }
        }
    }
    fun getScheduler(): Flow<List<Visit>>? {
        return visitDAO.getListClientScheduler()?.map{list->
            list.map { it.copy(priceScreen = it.price.toString()) }
        }
    }
}
