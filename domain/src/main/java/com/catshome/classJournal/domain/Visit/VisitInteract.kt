package com.catshome.classJournal.domain.Visit


import com.android.identity.util.UUID
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class VisitInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val visitRepository: VisitRepository
) {
    fun searchChild(searchText: String): Flow<List<MiniChild>?> {
        return childRepository.getChildByName(searchText)
    }

    suspend fun saveVisit(listVisit: List<Visit>) {
        listVisit.forEach { visit ->
            if (visit.uid.isEmpty())
                throw kotlin.IllegalArgumentException("Не указан UID ребенка")

            if (visit.price <= 0)
                throw IllegalArgumentException("Платеж не может быть нулевым или отрицательным.")

            visitRepository.insetPay(
                pay = Pay(
                    uidPay = if (payment.uidPay.isEmpty()) UUID.randomUUID().toString()
                    else payment.uidPay,
                    uidChild = uid,
                    datePay = payment.datePay,
                    payment = payment.payment
                )
            )

        }
    }
}