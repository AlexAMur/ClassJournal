package com.catshome.classJournal.domain.Pay

import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow

interface PayRepository {
    suspend fun getAllPays(isDelete: Boolean, sortEnum: SortEnum): Flow<List<Pay>>?
    suspend fun getPayByChild(uidChild: String, sortEnum: SortEnum): Flow<List<Pay>>?
    suspend fun getPayByChildWithPeriod(
        uidChild: String,
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Pay>>?

    suspend fun getPayByPeriod(
        begin: Long,
        end: Long,
        sortEnum: SortEnum
    ): Flow<List<Pay>>?
    suspend fun deletePay(pay: Pay): Boolean
    suspend fun insetPay(pay: Pay): Boolean
    suspend fun updatePay(pay: Pay): Boolean
}