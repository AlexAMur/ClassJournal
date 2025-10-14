package com.catshome.classJournal.child


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.communs.toDateStrungRU
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "child", indices = [Index(value = ["child_name", "child_surname", "child_birthday"], unique = true)])
data class ChildEntity(
    @PrimaryKey var uid: String,
    @ColumnInfo(name = "child_name") var name: String,
    @ColumnInfo(name = "child_surname") var surname: String,
    @ColumnInfo(name = "child_phone") var phone: String,
    @ColumnInfo(name = "child_note") var note: String,
    @ColumnInfo(name = "child_birthday") var birthday: Long,
    var isDelete: Boolean = false
)
fun ChildEntity.mapToChild(): Child{

   return Child(
       uid = this.uid,
       name = this.name,
       surname = this.surname,
       birthday = this.birthday.toDateStrungRU(),
       phone = this.phone,
       note = this.note,
       isDelete = this.isDelete
   )
}
fun Child.mapToChildEntity(): ChildEntity{
    val formatter =    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val t =formatter.parse(this.birthday)?.time?:0
    return ChildEntity(
        uid = this.uid,
        name = this.name.toString(),
        surname = this.surname.toString(),
        phone = this.phone.toString(),
        note = this.note.toString(),
        birthday = formatter.parse(this.birthday).time,
        isDelete = this.isDelete,
    )
}
