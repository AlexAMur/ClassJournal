package com.catshome.classJournal.child.DocChild

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDocFileDAO {
   @Transaction
   fun insertDos(listDocs: List<ChildDocFileEntity>){
       listDocs.map { insert(it) }
   }
    @Insert
    fun insert(childDocFileEntity: ChildDocFileEntity)
    @Delete
    fun delete(childDocFileEntity: ChildDocFileEntity)
    @Query("Select * from child_doc_files where child_id like :childId")
    fun docsByChileID(childId: String): Flow<List<ChildDocFileEntity>>
    @Update
    fun update(childDocFileEntity: ChildDocFileEntity)
    @Query("select *  from child_doc_files where id = :uid")
    fun selectDocById(uid: Long): ChildDocFileEntity
}