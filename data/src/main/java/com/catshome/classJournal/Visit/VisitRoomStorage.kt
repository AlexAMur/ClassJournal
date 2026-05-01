package com.catshome.classJournal.Visit

import android.util.Log
import com.catshome.classJournal.PayList.getSortString
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
    suspend fun save(visit: List<VisitEntity>): Boolean {
        visitDAO.insert(visit).forEach {
            if (it <= 0) return false
        }
        return true
    }

    suspend fun delete(visitEntity: VisitEntity): Boolean {
        return visitDAO.delete(visitEntity) > 0
    }

    fun getVisitAll(sortEnum: SortEnum): Flow<List<Visit>>? {
        return visitDAO.getFullVisit(
            isDelete = false,
            sortDate = if (getSortString(sortEnum) == VisitScreenEntity::dateVisit.name)
                VisitScreenEntity::dateVisit.name else "",
            sortSurname = if (getSortString(sortEnum) == VisitScreenEntity::Surname.name)
                VisitScreenEntity::Surname.name else "",
            sortName = if (getSortString(sortEnum) == VisitScreenEntity::Name.name)
                VisitScreenEntity::Name.name else ""
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
