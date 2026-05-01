package com.catshome.classJournal.Visit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.domain.Visit.Visit
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(visitEntity: List<VisitEntity>): List<Long>

    @Delete
    suspend fun delete(visitEntity: VisitEntity): Int

    @Update  (onConflict = REPLACE)
    suspend fun update(visitEntity: VisitEntity): Int
    @Query("Select v.uid , v.uidChild , c.child_name as Name, c.child_surname as Surname," +
            " v.dateVisit, v.priceVisit  from visits v join child c where v.uidChild =c.uid and" +
            " v.uid = :uidVisit")
    fun getVisitByUid(uidVisit: String): VisitScreenEntity?

    @Query(
        "Select v.uid , v.uidChild , c.child_name as Name, c.child_surname as Surname, v.dateVisit " +
                ", v.priceVisit  from visits v join child c where v.uidChild =c.uid and c.isDelete = :isDelete ORDER BY " +
                " CASE WHEN :sortDate = 'dateVisit' THEN dateVisit END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, " +
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getFullVisit(
        isDelete: Boolean,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<VisitScreenEntity>>?

    @Query(
        "Select v.uid , v.uidChild , c.child_name as Name, c.child_surname as Surname, v.dateVisit, " +
                "v.priceVisit  from visits v join child c where uidChild =c.uid and v.uidChild= :uid ORDER BY " +
                " CASE WHEN :sortDate = 'dateVisit' THEN dateVisit END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, " +
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getVisitByChildID(
        uid: String,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<VisitScreenEntity>>?

    @Query(
        "Select v.uid  , uidChild , c.child_name as Name, c.child_surname as Surname, dateVisit, " +
                "priceVisit  from visits v join child c where uidChild =c.uid and uidChild= :uid and " +
                "dateVisit>= :begin and dateVisit <= :end ORDER BY " +
                " CASE WHEN :sortDate = 'dateVisit' THEN dateVisit END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, " +
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getVisitByChildIDWithPeriod(
        uid: String,
        begin: Long,
        end: Long,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<VisitScreenEntity>>?

    @Query(
        "select v.uid , uidChild ,  c.child_name as Name, c.child_surname as Surname, " +
                "dateVisit, priceVisit from visits v join child c where uidChild =c.uid " +
                "and c.isDelete = 0 and dateVisit >= :begin and dateVisit <= :end ORDER BY " +
                " CASE WHEN :sortDate = 'dateVisit' THEN dateVisit END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC ," +
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getVisitByPeriod(
        begin: Long,
        end: Long,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<VisitScreenEntity>>?

//получаем список детей на определенный день в расписании
    @Query("select c.uid as uidChild, s.startlesson, null as groupName, " +
            "(c.child_name||' '||c.child_surname) as fio, 0 as 'check', s.price  from child c Join scheduler s where " +
            "c.uid = s.uidChild and dayOfWeek = :dayOfWeek and uidChild !='null' union " +
            "select c.uid as uidChild, s.startlesson, g.group_name as groupName," +
            "(c.child_name||' '||c.child_surname) as fio, 0 as 'check', s.price " +
            "from child c Join child_group cg Join `groups` g join scheduler s where " +
            "c.uid = cg.childId and g.uid = cg.groupId  and cg.groupId in (select uidGroup from " +
            "scheduler where dayOfWeek = :dayOfWeek and uidGroup !='null') and s.uidGroup = g.uid " +
            "order by startLesson")
fun getListClientScheduler(dayOfWeek: Int): Flow<List<Visit>>?


    //получаем список детей расписании
    @Query("select c.uid as uidChild,s.dayOfWeek, s.startlesson, null as groupName, " +
            "(c.child_name||' '||c.child_surname) as fio, 0 as 'check', s.price  from child c " +
            "Join scheduler s where c.uid = s.uidChild and uidChild !='null' union " +
            "select c.uid as uidChild, s.dayOfWeek, " +
            "s.startlesson, g.group_name as groupName,(c.child_name||' '||c.child_surname) as fio, " +
            "0 as 'check', s.price from child c Join child_group cg Join `groups` g join scheduler s " +
            "where c.uid = cg.childId and g.uid = cg.groupId  and cg.groupId in (select uidGroup from " +
            "scheduler where uidGroup !='null') and s.uidGroup = g.uid " +
            "order by dayOfWeek,startLesson")
    fun getListClientScheduler(): Flow<List<Visit>>?

//
//    @Query(
//        "select c.uid, s.startlesson, g.group_name as groupName, " +
//                "(c.child_name||' '||c.child_surname) as fio,c.child_name as name, " +
//                "c.child_surname as surname from child c Join child_group cg Join `groups` g " +
//                "join scheduler s where c.uid = cg.childId and g.uid = cg.groupId  and " +
//                "cg.groupId in (select uidGroup from scheduler where dayOfWeek =:dayOfWeek " +
//                "and uidGroup !='null') and s.uidGroup = g.uid"
//    )
//    fun getChildWithGroupSchedulerByDay(dayOfWeek: Int): Flow<List<VisitScheduler>>?
//    //получаем список детей на определенный день в расписании
//    @Query(
//        "select c.uid, s.startlesson, null as groupName, " +
//                "(c.child_name||' '||c.child_surname) as fio, c.child_name as name, " +
//                "c.child_surname as surname from child c Join scheduler s where c.uid = s.uidChild " +
//                "and dayOfWeek = :dayOfWeek and uidChild !='null'"
//    )
//    fun getChildSchedulerByDay(dayOfWeek: Int): Flow<List<VisitScheduler>>?
}