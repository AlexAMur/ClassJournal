package com.catshome.classJournal.domain.PayList

import android.R
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.CodeError
import com.catshome.classJournal.domain.SaveError
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

    suspend fun savePay(uid: String, payment: Pay){

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