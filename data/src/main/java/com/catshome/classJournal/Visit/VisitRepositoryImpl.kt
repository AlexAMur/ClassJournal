package com.catshome.classJournal.Visit

import androidx.lifecycle.LifecycleCoroutineScope
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitRepository
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject



class VisitRepositoryImpl @Inject constructor(val visitStorage: VisitRoomStorage): VisitRepository {
    override suspend fun getAllVisit(
        isDelete: Boolean,
        sortEnum: SortEnum
    ): Flow<List<Visit>>? {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun insetVisit(visit: List<Visit>) {

            visitStorage.saveVisit(visit.map { it.mapToVisitEntity()})

    }

    override suspend fun updateVisit(visit: Visit) {
        TODO("Not yet implemented")
    }

}