package com.catshome.classJournal.PayList

import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PayRepositoryImpl @Inject constructor(val storage: RoomPayStorage) : PayRepository {

    override suspend fun getAllPays(isDelete: Boolean): Flow<List<Pay>>? {
        return storage.getAllPay(isDelete)?.map {
            listPayEntity-> listPayEntity.map {
                it.mapToPay()
            }
        }
    }

    override suspend fun getPayByChild(uidChild: String): Flow<List<Pay>>? {
        return storage.getPayByChild(uidChild)?.map {list->
            list.map {
                it.mapToPay()
            }
        }
    }

    override suspend fun getPayByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long
    ): Flow<List<Pay>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPayByPeriod(
        begin: Long,
        end: Long
    ): Flow<List<Pay>>? {
        TODO("Not yet implemented")
    }

    override suspend fun deletePay(pay: Pay) {
        TODO("Not yet implemented")
    }

    override suspend fun insetPay(pay: Pay) {
        storage.insertPay(pay.mapToPayEntity())
    }

    override suspend fun updatePay(pay: Pay) {
        TODO("Not yet implemented")
    }
}