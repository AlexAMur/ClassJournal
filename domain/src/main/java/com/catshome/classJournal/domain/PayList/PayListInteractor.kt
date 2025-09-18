package com.catshome.classJournal.domain.PayList

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayListRepository) {
    fun getPays(): Flow<List<Pay>>{
        //TODO вернуть список платежей с ФИO
        return payListRepository.getPays()
    }
    fun deletePay(pay: Pay){
        payListRepository.delete(pay)
    }


}