package com.catshome.classJournal.domain.PayList

import kotlinx.coroutines.flow.Flow

interface PayListRepository {
   fun getPays():Flow<List<Pay>>
   fun delete(pay :Pay)
}