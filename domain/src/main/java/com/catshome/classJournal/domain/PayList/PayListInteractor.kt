package com.catshome.classJournal.domain.PayList


import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayRepository,
                                    val childRepository: ChildRepository) {

    suspend fun getPays(uid: String?, beginDate: Long?, endDate: Long?): Flow<List<Pay>>?{
            if (uid?.isNullOrEmpty() == false ) {
                if (beginDate != null && endDate != null) {
                    return payListRepository.getPayByChildWithPeriod(
                        uidChild = uid,
                        begin = beginDate,
                        end = endDate)
                }
                return payListRepository.getPayByChild(uid)
            } else{
                if (beginDate != null && endDate !=null)
                    return  payListRepository.getPayByPeriod(
                        begin = beginDate,
                        end = endDate
                    )
                else
                    return payListRepository.getAllPays(false)
            }
    }
    fun deletePay(pay: Pay){
      //  payListRepository.delete(pay)
    }
    suspend fun searchChild(searchText: String):Flow<List<MiniChild>?>{
        return childRepository.getChildByName(searchText)
    }
}