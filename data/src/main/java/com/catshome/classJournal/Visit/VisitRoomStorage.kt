package com.catshome.classJournal.Visit

import com.catshome.classJournal.PayList.PayScreenEntity
import com.catshome.classJournal.PayList.getSortString
import com.catshome.classJournal.Scheduler.SchedulerDAO
import com.catshome.classJournal.Scheduler.mapToScheduler
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VisitRoomStorage @Inject constructor(val visitDAO: VisitDAO, val schedulerDAO: SchedulerDAO) {
    suspend fun  save(visit: List<VisitEntity>): Boolean{
         visitDAO.insert(visit).forEach {
            if (it <= 0) return false
        }
        return true
    }
    suspend fun delete(visitEntity: VisitEntity): Boolean{
        return visitDAO.delete(visitEntity) > 0
    }
    fun  getVisitAll(sortEnum: SortEnum): Flow<List<Visit>>? {
      return visitDAO.getFullVisit(
          isDelete = false,
          sortDate = if(getSortString(sortEnum)== VisitScreenEntity::dateVisit.name)
              VisitScreenEntity::dateVisit.name else "",
          sortSurname = if(getSortString(sortEnum)== VisitScreenEntity::Surname.name)
              VisitScreenEntity::Surname.name else "",
          sortName = if(getSortString(sortEnum)== VisitScreenEntity::Name.name)
              VisitScreenEntity::Name.name else ""
      )?.map { list-> list.map{it.mapToVisit()}}
    }
    //расписание на один день
    fun getScheduler(dayOfWeek: DayOfWeek): Flow<List<Scheduler>>?{
      return schedulerDAO.getSchedulerByDay(dayOfWeek = dayOfWeek.ordinal)?.map {
          flow->
          flow.map { it.mapToScheduler() }

      }
    }
}