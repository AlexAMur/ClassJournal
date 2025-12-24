package com.catshome.classJournal.domain.PayList


import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayRepository,
                                    val childRepository: ChildRepository) {

    suspend fun getPays(uid: String?, beginDate: Long?, endDate: Long?, sort: SortEnum?): Flow<List<Pay>>?{
            if (uid?.isNullOrEmpty() == false ) {
                if (beginDate != null && endDate != null) {
                    return payListRepository.getPayByChildWithPeriod(
                        uidChild = uid,
                        begin = beginDate,
                        end = endDate,sort?: SortEnum.date_pay)
                }
                return payListRepository.getPayByChild(uid,sort?: SortEnum.date_pay)
            } else{
                if (beginDate != null && endDate !=null)
                    return  payListRepository.getPayByPeriod(
                        begin = beginDate,
                        end = endDate,
                        sort?: SortEnum.date_pay
                    )
                else
                    return payListRepository.getAllPays(false, sort?: SortEnum.date_pay)
            }
    }
    fun deletePay(pay: Pay){
      //  payListRepository.delete(pay)
    }
    suspend fun searchChild(searchText: String):Flow<List<MiniChild>?>{
        return childRepository.getChildByName(searchText)
    }
}