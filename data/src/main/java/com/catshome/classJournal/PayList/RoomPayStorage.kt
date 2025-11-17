package com.catshome.classJournal.PayList

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomPayStorage @Inject constructor(private val payDAO: PayDAO) {
    suspend fun insertPay(payEntity: PayEntity) {
        payDAO.insert(payEntity)
    }

    suspend fun updatePay(payEntity: PayEntity) {
        payDAO.update(payEntity)
    }

    suspend fun deletePay(payEntity: PayEntity) {
        payDAO.delete(payEntity)
    }

    suspend fun getAllPay(): Flow<List<PayEntity>>? {
        return payDAO.getFull()
    }

    suspend fun getPayByChild(uid: String): Flow<List<PayEntity>>? {
        return payDAO.getPayByChildID(uid = uid)
    }

    suspend fun getPayByChildWithPeriod(
        uid: String,
        begin: Long,
        end: Long
    ): Flow<List<PayEntity>>? {
        return payDAO.getPayByChildIDWithPeriod(
            uid = uid,
            begin = begin,
            end = end
        )
    }

    suspend fun getPayByPeriod(begin: Long, end: Long): Flow<List<PayEntity>>? {
        return payDAO.getPayByPeriod(
            begin = begin,
            end = end
        )
    }
}