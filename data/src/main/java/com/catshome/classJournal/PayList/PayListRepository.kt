package com.catshome.classJournal.PayList

import androidx.room.Room
import com.catshome.classJournal.domain.PayList.Pay
import com.catshome.classJournal.domain.PayList.PayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayListRepositoryImpl @Inject constructor(storsge: RoomPayStorage): PayListRepository {

    override fun getPays(): Flow<List<Pay>> {
        TODO("Not yet implemented")
    }

    override fun delete(pay: Pay) {
        TODO("Not yet implemented")
    }
}