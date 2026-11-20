package com.catshome.classJournal.docFiles

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.catshome.classJournal.resource.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object DocFileSaver {
    /*** Копирует файл по Uri во внутреннюю память приложения.
     * @return Абсолютный путь к скопированному файлу или null в случае ошибки.*/
    fun saveFileToInternalStorage(context: Context, sourceUri: Uri): String? {
        val contentResolver = context.contentResolver
       //0.
        val freeSpaceDisk = getAvailableSpaceInBytes(context.filesDir)
        val fileSize = getRequiredSpaceFromUri(context, sourceUri) ?: 0
        if (fileSize > freeSpaceDisk ) {
            Log.e("Storage", "Недостаточно места. Нужно: $fileSize, доступно: $freeSpaceDisk")
            return null
        }
        // 1. Получаем оригинальное имя файла
            val fileName = getFileName(context, sourceUri)
            // 2. Создаем целевую директорию внутри context.filesDir
            val targetDir = File(context.filesDir, context.getString(R.string.dir_docs))
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }
            // 3. Создаем уникальный файл (чтобы не перезаписать файлы с одинаковым именем)
            val targetFile = File(targetDir, "${System.currentTimeMillis()}_$fileName")
            return try {
                val inputStream: InputStream = contentResolver.openInputStream(sourceUri)
                    ?: throw IOException("Не удалось открыть InputStream для Uri: $sourceUri")
                val outputStream = FileOutputStream(targetFile)
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                // Возвращаем абсолютный путь, который запишем в Room
                targetFile.absolutePath

            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        /*** Вспомогательный метод для получения имени файла из Uri***/
        fun getFileName(context: Context, uri: Uri): String? {
            var name: String? = null
            if (uri.scheme == "content") {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index != -1) {
                            name = cursor.getString(index)
                        }
                    }
                }
            }
            if (name == null) {
                name = uri.path
                val cut = name?.lastIndexOf('/') ?: -1
                if (cut != -1) {
                    name = name?.substring(cut + 1)
                }
            }
            return name
        }
    }
