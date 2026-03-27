package com.catshome.classJournal.Visit

import javax.inject.Inject

class VisitRoomStorage @Inject constructor(val visitDAO: VisitDAO) {
    suspend fun  save(visit: List<VisitEntity>): Boolean{
         visitDAO.insert(visit).forEach {
            if (it <= 0) return false
        }
        return true
    }
    suspend fun delete(visitEntity: VisitEntity): Boolean{
        return visitDAO.delete(visitEntity) > 0
    }
}