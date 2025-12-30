package com.catshome.classJournal.domain.Visit

import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class VisitInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val payRepository: PayRepository
) {
    fun searchChild(searchText: String): Flow<List<MiniChild>?> {
        return childRepository.getChildByName(searchText)
    }

    suspend fun saveVisit(listVisit: List<Visit>){

        if (uid.isEmpty())
            throw IllegalArgumentException("Не указан UID ребенка")

        if (payment.payment.toInt()<=0)
            throw IllegalArgumentException("Платеж не может быть нулевым или отрицательным.")

        payRepository.insetPay(
            pay = Pay(
                uidPay = if(payment.uidPay.isEmpty())  UUID.randomUUID().toString()
                else payment.uidPay,
                uidChild = uid,
                datePay = payment.datePay,
                payment = payment.payment
            )
        )

    }
}