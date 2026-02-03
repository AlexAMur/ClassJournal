package com.catshome.classJournal.child
// для  удаления ребенка вызывать функцию deleteChild
// для  удаления ребенка вызывать функцию deleteChild
// для  удаления ребенка вызывать функцию deleteChild
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDAO {
    @Transaction
    suspend fun insertChild(child: ChildEntity, group: List<ChildGroupEntity>) {
        insert(child)
        if (group.isNotEmpty()) {
            group.forEach {
                insert(it)
            }
        }
    }
    //Обновляем данные ребенка и удаляем связанные данные в ChildGroup
    // И вставляет новые данные о группах
    @Transaction
    suspend fun updateChild(child: ChildEntity, group: List<ChildGroupEntity>) {
        update(child)
        deleteChildGroupByChildID(child.uid)
        if (group.isNotEmpty()) {
            group.forEach {
                insert(it)
            }
        }
    }
// для  удаления ребенка вызывать функцию deleteChild
    suspend fun deleteChild(child: ChildEntity) {
        //TODO удаление объектов связанных с ребенком
        if (checkChildGroupByChildID(child.uid) == 0)
            delete(child)
        else
            update(child.copy(isDelete = true))
    }

    @Insert
    suspend fun insert(childGroupEntity: ChildGroupEntity)

    @Insert
    suspend fun insert(child: ChildEntity)

    @Update(entity = ChildEntity::class)
    suspend fun update(child: ChildEntity)

    //    @Query("UPDATE child set child_name = :name, child_surname = :surname, " +
//            "child_birthday = :birthday ,  child_phone = :phone," +
//            " child_note =:note, isDelete = :isDelete where uid = :uid ")
//    suspend fun updateChild(
//        uid: String,
//        name: String,
//        surname: String,
//        birthday: Long,
//        phone: String,
//        note: String,
//        isDelete: Boolean
//    )
//TODO Удаление объектов
    //Связанные объекты ChildGroups удаляются автоматически
    @Delete
    suspend fun delete(child: ChildEntity)

    @Query("DELETE  from 'child_group' where childId = :childUid")
    fun deleteChildGroupByChildID(childUid: String)

    @Query("Select count()  from 'child_group' where childId = :childUid")
    fun checkChildGroupByChildID(childUid: String): Int

    @Query("Select * from 'child'")
    fun getFull(): Flow<List<ChildEntity>>

    @Query("select * from child where child_name = :name and child_surname = :surname" +
            " and child_birthday = :birthday and isDelete = :isDelete")
    fun findDeleteChild(
        name: String,
        surname: String,
        birthday: Long,
        isDelete: Boolean = true
    ): ChildEntity?

    @Query("Select * from 'child' where uid = :uid ")
    fun getChildById(uid: String): ChildEntity?

    @Query("Select (child_name ||' '|| child_surname)as fio,  uid, child_name, child_surname, child_birthday, child_note, child_phone, saldo, isDelete from 'child' where fio LIKE :name and isDelete = 0")
    fun getChildByName(name: String): Flow<List<ChildEntity>>

    @Query("SELECT * FROM 'child' where isDelete = :isDelete")
    fun getChilds(isDelete: Boolean): Flow<List<ChildEntity>>
}