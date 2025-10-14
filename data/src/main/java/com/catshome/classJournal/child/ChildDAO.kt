package com.catshome.classJournal.child

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.catshome.classJournal.domain.Child.Child
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface ChildDAO {
    @Transaction
    suspend fun insertChild(child: ChildEntity, group: List<ChildGroupEntity>){
        insert(child)
        deleteChildGroupByChildID(child.uid)
        group.forEach {
            insert(it)
        }

    }
    @Insert
    suspend fun insert(childGroupEntity: ChildGroupEntity)

    @Insert
    suspend fun insert(group: ChildEntity)

    @Update
    suspend fun update(group: ChildEntity)

    @Delete
    suspend fun delete(group: ChildEntity)

    @Query("DELETE  from 'child_group' where childId = :childUid")
    fun deleteChildGroupByChildID(childUid: String)

    @Query("Select * from 'child'")
    fun getFull(): Flow<List<ChildEntity>>

    @Query("Select * from 'child' where uid = :uid ")
    fun getChildById(uid: String): ChildEntity?

    @Query("Select * from 'child' where child_surname LIKE :surname")
    fun getChildByName(surname: String): Flow<List<ChildEntity>>

    @Query("SELECT * FROM 'child' where isDelete = :isDelete")
    fun getChilds(isDelete: Boolean): Flow<List<ChildEntity>>

}