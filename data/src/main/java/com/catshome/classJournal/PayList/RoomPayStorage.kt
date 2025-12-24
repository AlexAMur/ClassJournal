package com.catshome.classJournal.PayList

import android.util.Log
import com.catshome.classJournal.domain.communs.SortEnum
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

    suspend fun getAllPay(isDelete: Boolean, sortEnum: SortEnum): Flow<List<PayScreenEntity>>? {

        return payDAO.getFull(isDelete = isDelete,
            sortDate = if(getSortString(sortEnum)== PayScreenEntity::date_pay.name)
                PayScreenEntity::date_pay.name else "" ,
            sortName = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Name.name else "" ,
            sortSurname = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Surname.name else ""
        )
    }

    suspend fun getPayByChild(uid: String, sortEnum: SortEnum): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByChildID(uid = uid,
            sortDate = PayScreenEntity::date_pay.name,
            sortName = "",
            sortSurname = ""
        ) //getSortString(sortEnum))
    }

    suspend fun getPayByChildWithPeriod(
        uid: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByChildIDWithPeriod(
            uid = uid,
            begin = begin,
            end = end,
            sortDate = if(getSortString(sortEnum)== PayScreenEntity::date_pay.name)
                PayScreenEntity::date_pay.name else "" ,
            sortName = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Name.name else "" ,
            sortSurname = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Surname.name else ""
        )
    }

    suspend fun getPayByPeriod(begin: Long, end: Long, sortEnum: SortEnum): Flow<List<PayScreenEntity>>? {
        return payDAO.getPayByPeriod(
            begin = begin,
            end = end,
            sortDate = if(getSortString(sortEnum)== PayScreenEntity::date_pay.name)
                PayScreenEntity::date_pay.name else "" ,
            sortName = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Name.name else "" ,
            sortSurname = if(getSortString(sortEnum)== PayScreenEntity::Surname.name)
                PayScreenEntity::Surname.name else ""
        )
    }
}