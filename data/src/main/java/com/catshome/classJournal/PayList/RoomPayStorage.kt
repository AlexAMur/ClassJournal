package com.catshome.classJournal.PayList

import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomPayStorage @Inject constructor(private val payDAO: PayDAO) {
    suspend fun getStatisticPay(beginDate: Long?, endDate:Long?):Int{
      return  payDAO.getStatisticPay(beginDate, endDate)
    }
    suspend fun insertPay(payEntity: PayEntity): Boolean = payDAO.insertPay(payEntity)

    suspend fun updatePay(payEntity: PayEntity): Boolean {
        return payDAO.updatePay(payEntity)
    }

    suspend fun deletePay(payEntity: PayEntity): Boolean {
      return  payDAO.deletePay(payEntity)
    }

    suspend fun getAllPay(isDelete: Boolean, sortEnum: SortEnum): Flow<List<PayScreenEntity>>? {
        return payDAO.getFull(isDelete = isDelete,
            sortDate = if(sortEnum.name == SortEnum.Date.name)
                PayScreenEntity::date_pay.name else "",
            sortSurname = if(sortEnum.name == SortEnum.FIO.name)
                PayScreenEntity::Surname.name else ""
        )
    }

    suspend fun getPayByChild(uid: String): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByChildID(uid = uid)
    }

    suspend fun getPayByChildWithPeriod(
        uid: String,
        begin: Long,
        end: Long
    ): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByChildIDWithPeriod(
            uid = uid,
            begin = begin,
            end = end
        )
    }

    suspend fun getPayByPeriod(begin: Long, end: Long, sortEnum: SortEnum): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByPeriod(
            begin = begin,
            end = end,
            sortDate = if(sortEnum.name == SortEnum.Date.name)
                PayScreenEntity::date_pay.name else "" ,
            sortSurname = if(sortEnum.name == SortEnum.FIO.name )
                PayScreenEntity::Surname.name else ""
        )
    }
}