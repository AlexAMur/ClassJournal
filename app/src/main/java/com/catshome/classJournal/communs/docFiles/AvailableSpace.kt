package com.catshome.classJournal.communs.docFiles

import android.os.StatFs
import java.io.File

/**
 * Возвращает количество свободного места в байтах для указанной директории.
 */
fun getAvailableSpaceInBytes(dir: File): Long {
    return try {
        val stat = StatFs(dir.absolutePath)
        // Использование availableBytes предпочтительнее, так как оно учитывает
        // квоты системы и ограничения для обычных приложений
        stat.availableBytes
    } catch (e: Exception) {
        0L // Если что-то пошло не так, считаем, что места нет
    }
}
