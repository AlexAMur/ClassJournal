package com.catshome.classJournal.domain.Visit

import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.VisitSortEnum
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
 fun getSchedulerByDay(dayOfWeek: DayOfWeek):Flow<List<Visit>>?
 suspend fun getVisitByUid(uid: String):Visit?
 fun getScheduler():Flow<List<Visit>>?
    suspend fun getAllVisit(isDelete: Boolean, sortEnum: VisitSortEnum): Flow<List<Visit>>?
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
    suspend fun deleteVisit(visit: List<Visit>): Boolean
    suspend fun insetVisit(visit: List<Visit>)
    suspend fun updateVisit(visit: List<Visit>)
}