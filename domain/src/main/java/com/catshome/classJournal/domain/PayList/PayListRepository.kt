package com.catshome.classJournal.domain.PayList

import kotlinx.coroutines.flow.Flow

interface PayRepository {
   suspend fun getAllPays():Flow<List<Pay>>?
   suspend fun getPayByChild(uidChild :String):Flow<List<Pay>>?
   suspend fun getPayByChildWithPeriod(uidChild :String,begin: Long, end: Long):Flow<List<Pay>>?
   suspend fun getPayByPeriod(begin: Long, end: Long):Flow<List<Pay>>?
   suspend fun deletePay(pay :Pay)
   suspend fun insetPay(pay :Pay)
   suspend fun updatePay(pay :Pay)
}