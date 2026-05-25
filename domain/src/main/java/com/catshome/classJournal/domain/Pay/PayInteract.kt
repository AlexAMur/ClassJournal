package com.catshome.classJournal.domain.Pay

import android.util.Log
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PayInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val payRepository: PayRepository
) {
    fun searchChild(searchText: String): Flow<List<MiniChild>?> {
        return childRepository.getChildByName(searchText)
    }

    suspend fun savePay(uid: String, payment: Pay): Boolean{
        if (uid.isEmpty())
            throw IllegalArgumentException("Не указан UID ребенка")
        if (payment.payment <= 0)
            throw IllegalArgumentException("Платеж не может быть нулевым или отрицательным.")

        if(payment.uidPay.isNotEmpty())
            return payRepository.updatePay(payment)

       return payRepository.insetPay(
            pay = Pay(
                uidPay = payment.uidPay.ifEmpty { UUID.randomUUID().toString() },
                uidChild = uid,
                datePay = payment.datePay,
                payment = payment.payment
            )
       )
    }
}