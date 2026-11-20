package com.catshome.classJournal.docFiles
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun shareFile(context: Context, fileUri: Uri) {
    val filePath = fileUri.path ?: return
    val file = File(filePath)

    if (!file.exists()) {
        Toast.makeText(context, "Файл не найден", Toast.LENGTH_SHORT).show()
        return
    }

    val contentUri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val extension = file.extension.lowercase()
    val mimeType = MimeTypeMap.getSingleton()
        .getMimeTypeFromExtension(extension) ?: "*/*"

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = mimeType
        putExtra(Intent.EXTRA_STREAM, contentUri)
        // Обязательно для корректной выдачи прав в системном меню «Поделиться» на Android 10+
        clipData = ClipData.newRawUri(file.name, contentUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val chooser = Intent.createChooser(shareIntent, "Поделиться файлом").apply {
        if (context !is Activity) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    try {
        context.startActivity(chooser)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Нет приложений для отправки файла", Toast.LENGTH_SHORT).show()
    }
}
