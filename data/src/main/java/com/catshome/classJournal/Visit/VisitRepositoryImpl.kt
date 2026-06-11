package com.catshome.classJournal.Visit

import android.util.Log
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitRepository
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class VisitRepositoryImpl @Inject constructor(val visitStorage: VisitRoomStorage): VisitRepository {
    override  fun getSchedulerByDay(dayOfWeek: DayOfWeek): Flow<List<Visit>>? {
        return visitStorage.getScheduler(dayOfWeek = dayOfWeek)
    }
    override suspend fun getVisitByChildPeriod(
            uid: String,
            begin: Long,
            end: Long
            ): Flow<List<Visit>>? {
        return visitStorage.getVisitByChildPeriod(
            uidChild = uid,
            begin = begin,
            end = end
        )
    }
    override suspend fun getVisitByUid(uid: String): Visit? {
        return visitStorage.getVisitByUid(uid)
    }
    override fun getScheduler(): Flow<List<Visit>>? {
        return visitStorage.getScheduler()
    }

    override suspend fun getAllVisit(
        isDelete: Boolean,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
       return visitStorage.getVisitAll(sortEnum = sortEnum)
    }

    override suspend fun getVisitByChild(
        uidChild: String
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,

    ): Flow<List<Visit>>? {
        return visitStorage.getVisitByChildPeriod(
            uidChild = uidChild,
            begin = begin,
            end = end
        )
    }

    override suspend fun getVisitByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
     return  visitStorage.getVisitByPeriod(begin = begin, end = end, sortEnum = sortEnum)
    }

    override suspend fun deleteVisit(visit: List<Visit>): Boolean {

        return visitStorage.delete(visit.map{it.mapToVisitEntity()})
    }

    override suspend fun insetVisit(visit: List<Visit>) {
            visitStorage.save(visit.map { it.mapToVisitEntity()})
    }

    override suspend fun updateVisit(visit: List<Visit>) {
        visitStorage.update(visit.map { it.mapToVisitEntity()})
    }

}