package com.catshome.classJournal.communs.docFiles

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log

/*** Возвращает размер файла по Uri в байтах или null, если размер неизвестен. */
fun getRequiredSpaceFromUri(context: Context, uri: Uri): Long? {
    // Если это стандартный файловый Uri (file://), размер можно узнать напрямую
    if (uri.scheme == "file") {
        return uri.path?.let { java.io.File(it).length() }
    }
    // Для контентных Uri (content://) делаем запрос к ContentResolver
    return try {
        context.contentResolver.query(
            uri,
            arrayOf(OpenableColumns.SIZE), // Запрашиваем только колонку размера
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1 && !cursor.isNull(sizeIndex)) {
                    cursor.getLong(sizeIndex)
                } else {
                    null
                }
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("RequiredSpace", "Не удалось получить размер файла для Uri: $uri", e)
        null
    }
}
