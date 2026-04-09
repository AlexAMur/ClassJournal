package com.catshome.classJournal.Visit

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
        uidChild: String,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVisit(visit: Visit) {
        visitStorage.delete(visit.mapToVisitEntity())
    }

    override suspend fun insetVisit(visit: List<Visit>) {
            visitStorage.save(visit.map { it.mapToVisitEntity()})
    }

    override suspend fun updateVisit(visit: Visit) {
        TODO(" Not yet implemented")
    }

}