package com.catshome.classJournal.domain.Pay


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.catshome.classJournal.domain.Child.ChildRepository
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLong
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListInteractor @Inject constructor (val payListRepository: PayRepository,
                                    val childRepository: ChildRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getStatisticPay(beginDate: String?, endDate: String): Int{
        Log.e("CLJR", "ds- ${beginDate?.toLocalDateTimeRu()?.toLong()} ed-${endDate?.toLocalDateTimeRu()?.toLong()}")
        return payListRepository.getStatisticPay(beginDate?.toLocalDateTimeRu()?.toLong(), endDate?.toLocalDateTimeRu()?.toLong())
    }

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
    suspend fun deletePay(pay: Pay): Boolean{
       return payListRepository.deletePay(pay)
    }
    suspend fun searchChild(searchText: String):Flow<List<MiniChild>?>{
        return childRepository.getChildByName(searchText)
    }
}