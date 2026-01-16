package com.catshome.classJournal.domain.Visit


import android.os.Build
import androidx.annotation.RequiresApi
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.toLocalDateTime
import com.catshome.classJournal.domain.communs.toLong
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class VisitInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val visitRepository: VisitRepository
) {
    fun searchChild(searchText: String): Flow<List<MiniChild>?> {
        return childRepository.getChildByName(searchText)
    }

 @RequiresApi(Build.VERSION_CODES.O)
 suspend fun saveVisit(listVisit: List<Visit>) {
        listVisit.forEach { visit ->
            if (visit.uid.isEmpty())
                throw kotlin.IllegalArgumentException("Не указан UID")
            if (visit.uidChild.isEmpty())
                throw kotlin.IllegalArgumentException("Не указан UID ребенка")
            if (visit.data.isEmpty() || (visit.data.toLocalDateTime()?.toLong() ?: 0) > 0)
                throw kotlin.IllegalArgumentException("Нет или не корректная дата.")
            if (visit.price <= 0)
                throw IllegalArgumentException("Платеж не может быть нулевым или отрицательным.")
        }
     visitRepository.insetVisit(listVisit)
    }

    suspend fun getVisitAll(): Flow<List<Visit>>?{
       return visitRepository.getAllVisit(isDelete = false, SortEnum.Surname)
    }
}