package com.catshome.classJournal.domain.communs


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.datetime.DateTimeArithmeticException
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

const val DATE_FORMAT_RU = "dd.MM.yyyy"
const val DATETIME_FORMAT_RU = "dd.MM.yyyy HH:mm"
const val TIME_FORMAT = "HH:mm"

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTimeRu(): LocalDateTime? {
    var stringData =this
    if (this.length == DATE_FORMAT_RU.length)
        stringData = "$stringData 00:00"
    try {
        val formatter = LocalDateTime.Format {
            this@Format.day(padding = Padding.ZERO)
            char('.')
            monthNumber(padding = Padding.ZERO)
            char('.')
            year()
            char(' ')
            hour(padding = Padding.ZERO)
            char(':')
            minute()
        }

        return LocalDateTime.parse(stringData, formatter)
    } catch (e: RuntimeException) {
        Log.e("CLJR", "Ошибка форматирование ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateTimeRuString(): String {
    try {
        val formatter = LocalDateTime.Format {
            this@Format.day(padding = Padding.ZERO)
            char('.')
            monthNumber(padding = Padding.ZERO)
            char('.')
            year()
            char(' ')
            hour(padding = Padding.ZERO)
            char(':')
            minute()
            char(' ')
            }
        return this.format(formatter)
    } catch (_: IllegalArgumentException) {
        return "IllegalArgumentException"
    }
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
fun Long.toLocalDateTimeRu(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime? {
    try {
        val instant = Instant.fromEpochMilliseconds(this)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    } catch (_: DateTimeArithmeticException) {
        Log.e("CLJR","DateTimeArithmeticException of Long.toLocalDateTimeRu(): LocalDateTime?")
       return null
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun  Long.toLocalDateTimeRuString(timeZone: TimeZone = TimeZone.currentSystemDefault()):String?{
    return this.toLocalDateTimeRu(timeZone)?.toDateTimeRuString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toDateTimeRuString(timeZone: TimeZone): String{
  return this.toLocalDateTime(timeZone).toDateTimeRuString()
}

//fun Long.toDateStringRU(): String {
//    return convertMillisToDate(this)
//}
//
//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat(DATETIME_FORMAT_RU, Locale.getDefault())
//    return formatter.format(Date(millis))
//}
