package com.catshome.classJournal.PayList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDAO {
    @Query("select  sum(pay) from pays where date_pay between :startDate and :endDate")
    fun getStatisticPay(startDate: Long? = Long.MAX_VALUE ,endDate: Long? = Long.MAX_VALUE):Int

//    @Query("select c.uid as uidChild, sum(t.pay) as pay, sum(t.price) as price from child  c join (select p.uid_child as uidChild , p.pay, 0 as price  from pays p union all select v.uidChild, 0 as pay, v.priceVisit*-1 as price from visits v) t on t.uidChild = c.uid group by c.uid")
//    fun getBalance()

    @Insert(onConflict = ABORT)
    suspend fun insert(payEntity: PayEntity): Long
    @Delete
    suspend fun delete(payEntity: PayEntity):Int
    @Update
    suspend fun update(payEntity: PayEntity): Int
    @Query(
        "Select p.uid , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay " +
                ",p.pay  from pays p join child c where p.uid_child =c.uid and c.isDelete = :isDelete ORDER BY " +
                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getFull(
        isDelete: Boolean,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<PayScreenEntity>>?

    @Query(
        "Select p.uid , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
                "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid ORDER BY " +
                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getPayByChildID(
        uid: String,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<PayScreenEntity>>?

    @Query(
        "Select p.uid  , p.uid_child , c.child_name as Name, c.child_surname as Surname, p.date_pay, " +
                "p.pay  from pays p join child c where p.uid_child =c.uid and p.uid_child= :uid and " +
                "date_pay>= :begin and date_pay <= :end ORDER BY " +
                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC, "+
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getPayByChildIDWithPeriod(
        uid: String,
        begin: Long,
        end: Long,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<PayScreenEntity>>?

    @Query(
        "select p.uid , p.uid_child ,  c.child_name as Name, c.child_surname as Surname, " +
                "p.date_pay, p.pay from pays p join child c where p.uid_child =c.uid " +
                "and c.isDelete = 0 and p.date_pay >= :begin and date_pay <= :end ORDER BY " +
                " CASE WHEN :sortDate = 'date_pay' THEN date_pay END DESC ," +
                " CASE WHEN :sortSurname = 'Surname' THEN surname END ASC ,"+
                " CASE WHEN :sortName = 'Name' THEN name END ASC"
    )
    fun getPayByPeriod(
        begin: Long,
        end: Long,
        sortDate: String,
        sortName: String,
        sortSurname: String
    ): Flow<List<PayScreenEntity>>?
}