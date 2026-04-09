package com.catshome.classJournal.domain.Visit

import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
 fun getSchedulerByDay(dayOfWeek: DayOfWeek):Flow<List<Visit>>?
 fun getScheduler():Flow<List<Visit>>?
    suspend fun getAllVisit(isDelete: Boolean, sortEnum: SortEnum): Flow<List<Visit>>?
    suspend fun getVisitByChild(uidChild: String, sortEnum: SortEnum): Flow<List<Visit>>?
    suspend fun getVisitByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>?

    suspend fun getVisitByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>?
    suspend fun deleteVisit(visit: Visit)
    suspend fun insetVisit(visit: List<Visit>)
    suspend fun updateVisit(visit: Visit)
}