package com.catshome.classJournal.child


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.catshome.classJournal.domain.Child.Child
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "child")
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
   return Child()
}
fun Child.mapToChildEntity(): ChildEntity{
    val formatter =    SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
    
    return ChildEntity(
        uid = this.uid,
        name = this.name.toString(),
        surname = this.surname.toString(),
        phone = this.phone.toString(),
        note = this.note.toString(),
        birthday = formatter.parse(this.birthday)?.time?:0,
        isDelete = this.isDelete,
    )
}
