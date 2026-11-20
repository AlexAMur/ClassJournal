package com.catshome.classJournal.docFiles

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun openFileExternally(context: Context, fileUri: Uri) {
    // Получаем реальный путь из Uri (если передан file:///... или просто путь)
    val filePath = fileUri.path ?: return
    val file = File(filePath)

    if (!file.exists()) {
        Toast.makeText(context, "Файл не найден", Toast.LENGTH_SHORT).show()
        return
    }

    // Генерируем безопасный content:// Uri через FileProvider
    val contentUri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    // Определяем MIME-тип (для .pdf вернёт "application/pdf", для .jpg — "image/jpeg")
    val extension = file.extension.lowercase()
    val mimeType = MimeTypeMap.getSingleton()
        .getMimeTypeFromExtension(extension) ?: "*/*"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(contentUri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val chooser = Intent.createChooser(intent, "Открыть файл").apply {
        // Обязательно добавляем флаг сюда, так как запускается именно chooser
        if (context !is android.app.Activity) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    try {
        context.startActivity(chooser)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Нет приложения для просмотра этого файла", Toast.LENGTH_SHORT).show()
    }
}
