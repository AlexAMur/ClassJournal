package com.catshome.classJournal.child

import android.R.attr.name
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.Group.Models.GroupEntity
import com.catshome.classJournal.domain.Child.MiniChild
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildGroupDAO {
    @Insert
    suspend fun insert(childGroup: ChildGroupEntity)

    @Update
    suspend fun update(childGroup: ChildGroupEntity)

    @Delete
    suspend fun delete(group: ChildGroupEntity)

    @Query("Select * from 'groups' where isDelete = 0")
    fun getGroups(): Flow<List<GroupEntity>>


    @Query("delete from 'child_group' where childId LIKE :uidChild")
    fun deleteByChildUID(uidChild: String)

    @Query("Select * from 'child_group' where childId= :child_uid")
    fun getGroupByIdChild(child_uid: String): Flow<List<ChildGroupEntity>>

    @Query("select c.uid as childUid , c.child_name as childName, c.child_surname as childSurname,c.child_birthday as childBirthday , g.group_name as groupName, g.uid as groupUid  from child as c  left join   'child_group' as c_g on c_g.childId =c.uid left join  `groups` as g on groupId = g.uid or groupId =null and c.isDelete =:isDelete")
    fun getChildAndGroups(isDelete: Boolean): List<ChildWithGroupListEntity>
//Поиск по названию группы
    @Query("Select uid , group_name  , isDelete  from 'groups' where group_name LIKE :name and  isDelete = 0")
    fun getGroupsByName(name: String): Flow<List<GroupEntity>>
//Поиск по имени
    @Query("Select uid, (child_name|' '|child_surname) as fio , child_name as name, " +
            "child_surname as surname  from child where name LIKE :name or surname Like :name" +
            " and  isDelete = 0")
    fun getChildByName(name: String): Flow<List<MiniChild>>
}