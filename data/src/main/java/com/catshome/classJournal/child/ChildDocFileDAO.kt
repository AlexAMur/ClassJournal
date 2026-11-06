package com.catshome.classJournal.child

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDocFileDAO {
    @Delete
    fun delete(docFileEntity: ChildDocFileEntity)
}