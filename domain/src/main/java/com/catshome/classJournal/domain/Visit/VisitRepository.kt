package com.catshome.classJournal.domain.Visit

import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
    suspend fun getAllVisit(isDelete: Boolean, sortEnum: SortEnum): Flow<List<Visit>>?
    suspend fun getPayByChild(uidChild: String, sortEnum: SortEnum): Flow<List<Visit>>?
    suspend fun getPayByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>?

    suspend fun getPayByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Visit>>?
    suspend fun deleteVisit(visit: Visit)
    suspend fun insetVisit(visit: Visit)
    suspend fun updateVisit(visit: Visit)
}