package com.catshome.classJournal.docFiles

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import java.io.File

fun Context.deleteFileByUri(uri: Uri): Boolean {
    return try {
        when (uri.scheme) {
            "content" -> {
                // Если это документ из Storage Access Framework (SAF)
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    DocumentsContract.deleteDocument(contentResolver, uri)
                } else {
                    // Обычный ContentProvider (например, MediaStore)
                    contentResolver.delete(uri, null, null) > 0
                }
            }
            "file" -> {
                uri.path?.let { path ->
                    File(path).delete()
                } ?: false
            }
            else -> false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}