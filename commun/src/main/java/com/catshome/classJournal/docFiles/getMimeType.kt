package com.catshome.classJournal.docFiles

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File

fun Uri.getMimeType(context: Context): String? {
    return if (this.scheme == ContentResolver.SCHEME_CONTENT) {
        // Если это content-URI, спрашиваем ContentResolver
        context.contentResolver.getType(this)
    } else {
        // Если это file-URI или обычный путь, определяем по расширению
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(this.toString())
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension?.lowercase())
    }
}