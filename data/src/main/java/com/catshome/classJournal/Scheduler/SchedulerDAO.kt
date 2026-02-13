package com.catshome.classJournal.Scheduler

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.catshome.classJournal.PayList.PayEntity
import com.catshome.classJournal.PayList.PayScreenEntity
import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

@Dao
interface SchedulerDAO{
    @Transaction
    suspend fun deleteLesson(dayOfWeek: Int,
                             time: Long): Boolean{
        return deleteByTimeLesson(dayOfWeek,time)>0
    }
    @Transaction
    suspend fun saveScheduler(dayOfWeek: Int,
                              startLesson: Long,
                              list: List<SchedulerEntity>)
    {
        //deleteLesson(dayOfWeek= dayOfWeek, startLesson = startLesson)
        insert(list)

    }
    @Insert(onConflict = ABORT)
    suspend fun insert(schedulerEntity: List<SchedulerEntity>)

    @Delete
    suspend fun delete(schedulerEntity: SchedulerEntity): Int

    @Update
    suspend fun update(schedulerEntity:SchedulerEntity)


    @Query(
        "Select s.uid , s.uidChild,s.uidGroup, dayOfWeek,startLesson, duration ," +
                " c.child_name as name, c.child_surname as Surname ,group_name as groupName" +
                " from scheduler s left join child c on uidChild =c.uid left join 'groups' g" +
                " on  uidGroup = g.uid group by s.uid ORDER BY dayOfWeek ASC, 'Surname', 'Name' ASC"
    )
    fun getFull(): Flow<List<SchedulerScreenEntity>>?
    @Query(
        "Select s.uid , s.uidChild,s.uidGroup, dayOfWeek,startLesson, duration ," +
                " c.child_name as name, c.child_surname as Surname ,group_name as groupName from scheduler s join child c , 'groups' g" +
                " where uidChild =c.uid and uidGroup = g.uid and dayOfWeek = :dayOfWeek  ORDER BY dayOfWeek ASC, 'Surname', 'Name' ASC"
    )
    fun getSchedulerByDay(dayOfWeek: Int): Flow<List<SchedulerScreenEntity>>?
    @Query(
        "Select s.uid , s.uidChild,s.uidGroup, dayOfWeek,startLesson, duration ," +
                " c.child_name as name, c.child_surname as Surname ,group_name as groupName " +
                "from scheduler s join child c , 'groups' g" +
                " where uidChild =c.uid and uidGroup = g.uid" +
                " and dayOfWeek = :dayOfWeek  and startLesson = :startTime ORDER BY dayOfWeek ASC, 'Surname', 'Name' ASC"
    )
    fun getClientsSchedulerByLesson (dayOfWeek: Int, startTime: Long): Flow<List<SchedulerScreenEntity>>?
    @Query(
        "Delete from scheduler where startLesson = :time and dayOfWeek = :dayOfWeek"
    )
    fun deleteByTimeLesson(dayOfWeek: Int, time: Long):Int
}