package com.catshome.classJournal.Visit

import androidx.lifecycle.LifecycleCoroutineScope
import com.catshome.classJournal.domain.Scheduler.Scheduler
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitRepository
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject



class VisitRepositoryImpl @Inject constructor(val visitStorage: VisitRoomStorage): VisitRepository {
    override suspend fun getSchedulerByDay(dayOfWeek: DayOfWeek): Flow<List<Scheduler>>? {
        return visitStorage.getScheduler(dayOfWeek = dayOfWeek)
    }

    override suspend fun getAllVisit(
        isDelete: Boolean,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
       return visitStorage.getVisitAll(sortEnum = sortEnum)
    }

    override suspend fun getPayByChild(
        uidChild: String,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPayByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPayByPeriod(
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