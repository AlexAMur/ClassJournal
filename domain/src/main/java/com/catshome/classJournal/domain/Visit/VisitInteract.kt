package com.catshome.classJournal.domain.Visit


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class VisitInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val visitRepository: VisitRepository,
) {
    suspend fun getVisitByUid(uidVisit: String): Visit? {
        return visitRepository.getVisitByUid(uidVisit)
    }

    fun searchChild(searchText: String): Flow<List<MiniChild>?> {
        return childRepository.getChildByName(searchText)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveVisit(listVisit: List<Visit>, newVisit: Boolean) {
        listVisit.forEach { visit ->
            if (visit.uid.isNullOrEmpty())
                throw kotlin.IllegalArgumentException("Не указан UID")
            if (visit.uidChild.isNullOrEmpty())
                throw kotlin.IllegalArgumentException("Не указан UID ребенка")
            if (visit.data.isNullOrEmpty() || visit.data.toLocalDateTimeRu()?.toLong() == null)
                throw kotlin.IllegalArgumentException("${visit.data} Нет или не корректная дата.")
            if (visit.price == null || visit.price <= 0)
                throw IllegalArgumentException("Платеж не может быть нулевым или отрицательным.")
        }
        if (newVisit)
            visitRepository.insetVisit(listVisit)
        else
            visitRepository.updateVisit(visit = listVisit)


    }

    suspend fun getVisitAll(): Flow<List<Visit>>? {
        return visitRepository.getAllVisit(isDelete = false, SortEnum.Date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getVisitByPeriod(
        uidChild: String?,
        begin: Long,
        end: Long
    ): Flow<List<Visit>>? {
        if (uidChild != null) {
            return visitRepository.getVisitByChildWithPeriod(
                uidChild = uidChild,
                begin = begin,
                end = end
            )
        }
        else
            return visitRepository.getVisitByPeriod(
                begin = begin,
                end = end,
                sortEnum = SortEnum.Date
            )
    }

    fun getSchedulerByDay(dayOfWeek: DayOfWeek): Flow<List<Visit>>? {
        return visitRepository.getSchedulerByDay(dayOfWeek)
    }

    fun getScheduler(): Flow<List<Visit>>? {
        return visitRepository.getScheduler()
    }

    suspend fun deleteVisit(uidVisit: String): Boolean {

        val job = CoroutineScope(Dispatchers.IO).async {
            return@async visitRepository.getVisitByUid(uidVisit)?.let {
                visitRepository.deleteVisit(listOf(it))
            } == true
        }
        return job.await()
    }
}