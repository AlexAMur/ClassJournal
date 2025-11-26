package com.catshome.classJournal.domain.PayList


import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayRepository,
                                    val childRepository: ChildRepository) {

    suspend fun getPays(): Flow<List<Pay>>?{
         return payListRepository.getAllPays(false)

    }
    fun deletePay(pay: Pay){
      //  payListRepository.delete(pay)
    }

    fun savePay(): Boolean{
        TODO("Save pay emplamented")
        //payListRepository.insertPay(Pay)
    }
    suspend fun searchChild(searchText: String):Flow<List<MiniChild>?>{
        return childRepository.getChildByName(searchText)
    }


}