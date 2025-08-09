package com.catshome.classJournal.child

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.domain.Child.Child
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: ChildEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(group: ChildEntity)

    @Delete
    suspend fun delete(group: ChildEntity)

    @Query("Select * from 'child'")
    fun getFull(): Flow<List<ChildEntity>>

    @Query("Select * from 'child' where uid= :uid")
    fun getChildById(uid: Long): ChildEntity?

    @Query("Select * from 'child' where child_surname LIKE :surname")
    fun getChildByName(surname: String): ChildEntity?

    @Query("SELECT * FROM 'child' where isDelete = :isDelete")
    fun getChild(isDelete: Boolean): Flow<List<ChildEntity>>

}