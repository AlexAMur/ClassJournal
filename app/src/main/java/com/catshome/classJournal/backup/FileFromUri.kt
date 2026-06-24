package com.catshome.classJournal.backup

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

fun getFileFromUri(context: Context, uri: Uri): File? {
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val fileName = nameIndex?.let { returnCursor.getString(it) } ?: "temp_file"
    returnCursor?.close()

    val file = File(context.cacheDir, fileName)
    try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
    } catch (e: Exception) {
        return null
    }
    return file
}