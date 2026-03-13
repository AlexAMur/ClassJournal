package com.catshome.classJournal.PayList

import com.catshome.classJournal.domain.Pay.Pay
import com.catshome.classJournal.domain.Pay.PayRepository
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class PayRepositoryImpl @Inject constructor(val storage: RoomPayStorage) : PayRepository {

    override suspend fun getAllPays(isDelete: Boolean,  sortEnum: SortEnum): Flow<List<Pay>>? {

        return storage.getAllPay(isDelete, sortEnum)?.map {
            listPayEntity-> listPayEntity.map {
                it.mapToPay()
            }
        }
    }

    override suspend fun getPayByChild(uidChild: String, sortEnum: SortEnum): Flow<List<Pay>>? {
        return storage.getPayByChild(uidChild, sortEnum)?.map {list->
            list.map {
                it.mapToPay()
            }
        }
    }

    override suspend fun getPayByChildWithPeriod(
        uidChild: String,
        begin: Long, end: Long, sortEnum: SortEnum
    ): Flow<List<Pay>>? {
       return storage.getPayByChildWithPeriod(
           uid = uidChild,
           begin = begin,
           end = end,
           sortEnum
       )?.map {list->
           list.map {
               it.mapToPay()
           }
       }
    }

    override suspend fun getPayByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Pay>>? {
        return storage.getPayByPeriod(
            begin = begin,
            end = end, sortEnum)?.map {list->
            list.map {
                it.mapToPay()
            }
        }
    }

    override suspend fun deletePay(pay: Pay):Boolean {
        return storage.deletePay(pay.mapToPayEntity())
    }

    override suspend fun insetPay(pay: Pay):Boolean{
       return storage.insertPay(pay.mapToPayEntity())
    }

    override suspend fun updatePay(pay: Pay):Boolean {
        return storage.updatePay(pay.mapToPayEntity())
    }
}