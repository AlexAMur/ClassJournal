package com.catshome.classJournal.domain.communs


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.datetime.*

import kotlin.time.Instant


const val DATE_FORMAT_RU = "dd.MM.yyyy"
const val DATETIME_FORMAT_RU = "dd.MM.yyyy HH:mm"
const val TIME_FORMAT = "HH:mm"

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTimeRu(): LocalDateTime? {

    try {
        val formatter = LocalDateTime.Format { DATETIME_FORMAT_RU }
        return LocalDateTime.parse(this, formatter)
    } catch (e: RuntimeException) {
        Log.e("CLJR", "Ошибка форматирование ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateTimeRuString(): String {
    try {
        val formatter = LocalDateTime.Format { DATETIME_FORMAT_RU }
        return this.format(formatter)
    } catch (_: IllegalArgumentException) {
        return "IllegalArgumentException"
    }
//   catch (_: DateTimeException){
//     return null
//   }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateRuString(): String {
    val formatter = LocalDateTime.Format { DATETIME_FORMAT_RU }
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toLong(): Long {
    return this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTimeRu(): LocalDateTime? {
    try {
        val instant = Instant.fromEpochMilliseconds(this)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    } catch (_: DateTimeArithmeticException) {
        Log.e("CLJR","DateTimeArithmeticException of Long.toLocalDateTimeRu(): LocalDateTime?")
       return null
    }
}

//fun Long.toDateStringRU(): String {
//    return convertMillisToDate(this)
//}
//
//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat(DATETIME_FORMAT_RU, Locale.getDefault())
//    return formatter.format(Date(millis))
//}
