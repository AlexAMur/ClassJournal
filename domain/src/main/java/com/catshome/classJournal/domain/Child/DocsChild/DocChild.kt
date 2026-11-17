package com.catshome.classJournal.domain.Child.DocsChild

import com.android.identity.documenttype.DocumentAttributeType

data class DocChild(
    val id: Long,
    val childId: String,
    val fileUri: String,
    val fileName: String?, // Имя файла для отображения в UI (например, "snils_photo.jpg")
    val mimeType: String?, // Тип файла (например, "image/jpeg", "application/pdf") для корректного открытия
    val noteFile: String?, // Описание файла
    val addedAt: Long = System.currentTimeMillis(), // Время добавления файла в БД
    val isDelete: Boolean = false, // признак для удаления из базы
    val isNew: Boolean = false // признак для удаления из базы

)
