package com.catshome.classJournal.Visit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.catshome.classJournal.PayList.PayEntity
import com.catshome.classJournal.PayList.PayScreenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDAO {
    @Insert(onConflict = ABORT)
    suspend fun insert(visitEntity: List<VisitEntity>)
    @Delete
    suspend fun delete(visitEntity: VisitEntity)
    @Update
    suspend fun update(visitEntity: VisitEntity)
    @Query(
        "Select v.uid , v.uidChild , c.child_name as Name, c.child_surname as Surname, v.dateVisit " +
                ", v.priceVisit  from visits v join child c where v.uidChild =c.uid and c.isDelete = :isDelete ORDER BY " +
                " CASE WHEN :sortDate = 'dateVisit' THEN dateVisit END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
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
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
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
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
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
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC ,"+
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getVisitByPeriod(
        begin: Long,
        end: Long,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<VisitScreenEntity>>?
}