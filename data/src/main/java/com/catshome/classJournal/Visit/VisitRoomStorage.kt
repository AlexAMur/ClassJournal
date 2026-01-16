package com.catshome.classJournal.Visit

import javax.inject.Inject

class VisitRoomStorage @Inject constructor(val visitDAO: VisitDAO) {
    suspend fun  saveVisit(visit: List<VisitEntity>){
        visitDAO.insert(visit)
    }
}