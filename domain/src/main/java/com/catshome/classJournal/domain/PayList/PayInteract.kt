package com.catshome.classJournal.domain.PayList

import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.CodeError
import com.catshome.classJournal.domain.SaveError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayInteract @Inject constructor(
    private val childRepository: ChildRepository,
    private val payRepository: PayRepository
) {
    fun searchChild(searchText: String): Flow<List<Child>?> {
        return childRepository.getChildByName(searchText)
    }

    suspend fun savePay(child: Child, payment: Pay): SaveError {
        if (child.uid.isEmpty())
            return SaveError(
                code = CodeError.primaryKeyEmpty
            )
        if (payment.payment.toInt()<=0)
            return SaveError(code = CodeError.invalidArgument)
        payRepository.insetPay(
            pay = Pay(
                uidPay = payment.uidPay,
                uidChild = child.uid,
                datePay = payment.datePay,
                payment = payment.payment
            )
        )
        return SaveError(CodeError.Sucessfful)
    }
}