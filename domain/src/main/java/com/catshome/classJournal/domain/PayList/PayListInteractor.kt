package com.catshome.classJournal.domain.PayList

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayRepository) {

    suspend fun getPays(): Flow<List<Pay>>?{
         return payListRepository.getAllPays()

    }
    fun deletePay(pay: PayItem){
      //  payListRepository.delete(pay)
    }

    fun savePay(): Boolean{
        TODO("Save pay emplamented")
        //payListRepository.insertPay(Pay)
    }


}