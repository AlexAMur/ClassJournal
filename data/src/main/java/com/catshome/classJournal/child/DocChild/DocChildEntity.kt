package com.catshome.classJournal.child.DocChild

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.catshome.classJournal.child.ChildEntity
import com.catshome.classJournal.domain.Child.DocsChild.DocChild

@Entity(
    tableName = "child_doc_files",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class, // Предполагается наличие сущности ребенка
            parentColumns = ["uid"],
            childColumns = ["child_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["child_id","added_at"], orders = [Index.Order.ASC, Index.Order.DESC], name = "doc_child_index")]
)
data class ChildDocFileEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "child_id")
    val childId: String,
    @ColumnInfo(name = "file_uri")
    val fileUri: String, // Путь к файлу: Uri.toString() (content://...) или абсолютный путь к файлу в кэше/хранилище
    @ColumnInfo(name = "file_name")
    val fileName: String, // Имя файла для отображения в UI (например, "snils_photo.jpg")
    @ColumnInfo(name = "mime_type")
    val mimeType: String?, // Тип файла (например, "image/jpeg", "application/pdf") для корректного открытия
    @ColumnInfo(name = "note_file")
    val noteFile: String?, // Описание файла
    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis() // Время добавления файла в БД
)
fun ChildDocFileEntity.mapToDocChild(): DocChild{
    return DocChild(
        id = this.id,
        childId = this.childId,
        fileUri =  this.fileUri,
        fileName = this.fileName,
        mimeType = this.mimeType,
        noteFile = this.noteFile,
        addedAt = this.addedAt,
        isDelete = false
    )
}
fun DocChild.mapToChildDocFileEntity(): ChildDocFileEntity{
    return ChildDocFileEntity(
        id = this.id,
        childId = this.childId,
        fileUri =  this.fileUri,
        fileName = this.fileName.toString(),
        mimeType = this.mimeType,
        noteFile = this.noteFile,
        addedAt = this.addedAt
    )
}