package com.catshome.classJournal.Scheduler

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.PayList.PayEntity
import com.catshome.classJournal.PayList.PayScreenEntity
import com.catshome.classJournal.domain.Scheduler.Scheduler
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

@Dao
interface SchedulerDAO{
    @Insert(onConflict = ABORT)
    suspend fun insert(schedulerEntity: SchedulerEntity)

    @Delete
    suspend fun delete(schedulerEntity: SchedulerEntity)

    @Update
    suspend fun update(schedulerEntity:SchedulerEntity)

    @Query(
        "Select s.uid , s.uidChild,s.uidGroup, dayOfWeek,startLesson, duration ," +
                " c.child_name as name, c.child_surname as Surname ,group_name as groupName from scheduler s join child c , 'groups' g" +
                " where uidChild =c.uid and uidGroup = g.uid ORDER BY dayOfWeek ASC, 'Surname', 'Name' ASC"
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
//
//    @Query(
//        "Select p.uid , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
//                "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid ORDER BY " +
//                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
//                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
//                " CASE WHEN :sortName = 'Name' THEN name END ASC"
//    )
//    fun getPayByChildID(
//        uid: String,
//        sortDate: String,
//        sortName: String,
//        sortSurname: String
//    ): Flow<List<PayScreenEntity>>?
//
//    @Query(
//        "Select p.uid  , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
//                "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid and " +
//                "date_pay>= :begin and date_pay <= :end ORDER BY " +
//                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
//                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
//                " CASE WHEN :sortName = 'Name' THEN name END ASC"
//    )
//    fun getPayByChildIDWithPeriod(
//        uid: String,
//        begin: Long,
//        end: Long,
//        sortDate: String,
//        sortName: String,
//        sortSurname: String
//    ): Flow<List<PayScreenEntity>>?
//
//    @Query(
//        "select p.uid , p.uid_child ,  c.child_name as Name, c.child_surname as Surname, " +
//                "p.date_pay, p.pay from pays p join child c where p.uid_child =c.uid " +
//                "and c.isDelete = 0 and p.date_pay >= :begin and date_pay <= :end ORDER BY " +
//                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
//                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC ,"+
//                " CASE WHEN :sortName = 'Name' THEN name END ASC"
//    )
//    fun getPayByPeriod(
//        begin: Long,
//        end: Long,
//        sortDate: String,
//        sortName: String,
//        sortSurname: String
//    ): Flow<List<PayScreenEntity>>?
}