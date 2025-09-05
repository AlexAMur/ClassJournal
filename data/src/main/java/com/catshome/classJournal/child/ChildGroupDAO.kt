package com.catshome.classJournal.child

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.Group.Models.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildGroupDAO {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(childGroup: ChildGroupEntity)

        @Update(onConflict = OnConflictStrategy.REPLACE)
        suspend fun update(childGroup: ChildGroupEntity)

        @Delete
        suspend fun delete(group: ChildGroupEntity)

        @Query("Select * from 'groups' where isDelete = false")
          fun getGroups(): Flow<List<GroupEntity>>

        @Query("Select * from 'child_group' where childId= :child_uid")
        fun getGroupByIdChild(child_uid: String): ChildGroupEntity?

}