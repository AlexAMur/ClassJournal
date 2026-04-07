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
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaZoneId
import kotlinx.datetime.toLocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException
import kotlin.time.Instant

const val DATE_FORMAT_RU = "dd.MM.yyyy"
const val DATETIME_FORMAT_RU = "dd.MM.yyyy HH:mm"
const val TIME_FORMAT = "HH:mm"
enum class FormatDate{DateTime,Date, Time}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTimeRu(): LocalDateTime? {
    var stringData = this
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
            minute(padding = Padding.ZERO)
        }
        return LocalDateTime.parse(stringData, formatter)
    } catch (e: RuntimeException) {
        Log.e("CLJR", "Ошибка форматирование ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateTimeRuString(formatDate: FormatDate = FormatDate.DateTime): String? {
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
            minute(padding = Padding.ZERO)
        }
        val date  = this.format(formatter)
        when(formatDate){
            FormatDate.DateTime -> return date
            FormatDate.Date ->return date.substring(0 , DATE_FORMAT_RU.length)
            FormatDate.Time ->return date.substring(DATE_FORMAT_RU.length,date.length)
        }
    } catch (e: IllegalArgumentException) {
        Log.e("CLJR", "From toDateTimeRuString ${e.message}")
        return null
    } catch (e: Exception) {
        Log.e("CLJR", "From toDateTimeRuString ${e.message}")
        return null
    }
}

//
//@RequiresApi(Build.VERSION_CODES.O)
//fun LocalDateTime.toDateRuString(): String {
//    val formatter = LocalDateTime.Format { DATETIME_FORMAT_RU }
//    return this.format(formatter)
//}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toLong(): Long {
    return this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTimeRu(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime? {
    try {
        val instant = Instant.fromEpochMilliseconds(this)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    } catch (e: DateTimeArithmeticException) {
        Log.e("CLJR", "From Long.toLocalDateTimeRu() ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTimeRuString(timeZone: TimeZone = TimeZone.currentSystemDefault(),formatDate: FormatDate = FormatDate.DateTime): String? {
    return this.toLocalDateTimeRu(timeZone = timeZone)?.toDateTimeRuString(formatDate)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toDateTimeRuString(timeZone: TimeZone = TimeZone.currentSystemDefault(),formatDate: FormatDate = FormatDate.DateTime): String? {
    return this.toLocalDateTime(timeZone).toDateTimeRuString(formatDate)
}
//fun Int.toTimeRu():String?{
//   if (this.th)
//}