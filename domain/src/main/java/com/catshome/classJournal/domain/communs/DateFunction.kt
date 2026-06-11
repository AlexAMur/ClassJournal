package com.catshome.classJournal.domain.communs


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.datetime.DateTimeArithmeticException
import kotlinx.datetime.LocalDate
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
import java.util.Date
import kotlin.time.Clock
import kotlin.time.Instant

const val DATE_FORMAT_RU = "dd.MM.yyyy"
const val DATETIME_FORMAT_RU = "dd.MM.yyyy HH:mm"
const val TIME_FORMAT = "HH:mm"

enum class FormatDate { DateTime, Date, Time,Month,Day }
fun LocalDate.toStringRu(): String{
//    try {
        val formatter = LocalDate.Format {
            this@Format.day(padding = Padding.ZERO)
            char('.')
            monthNumber(padding = Padding.ZERO)
            char('.')
            year()
        }
        return this.format(formatter)
    //    return LocalDateTime.parse(stringData, formatter)
//    } catch (e: RuntimeException) {
//        Log.e("CLJR", "Ошибка форматирование ${e.message}")
//       return null
//    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun getNow(): LocalDateTime {
    return Clock.System.now().toEpochMilliseconds().toLocalDateTimeRu()!!
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTimeRu(timeZone: TimeZone): Date? {
    this.toLocalDateTimeRu()?.toLong()?.let {
        return Date(it + timeZone.offsetAt(Clock.System.now()).totalSeconds * 1000)
    }
    return null
}

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
        LocalDateTime.Format {
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
        return this.format(
         when (formatDate) {
            FormatDate.DateTime -> {
                LocalDateTime.Format {
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
            }
            FormatDate.Date -> {
                LocalDateTime.Format {
                    this@Format.day(padding = Padding.ZERO)
                    char('.')
                    monthNumber(padding = Padding.ZERO)
                    char('.')
                    year()
                }
            }
            FormatDate.Time ->{
                LocalDateTime.Format {
                    hour(padding = Padding.ZERO)
                    char(':')
                    minute(padding = Padding.ZERO)
                }
            }
            FormatDate.Month -> {
                LocalDateTime.Format {
                    monthNumber(padding = Padding.ZERO)
                }
            }
            FormatDate.Day -> {
                LocalDateTime.Format {
                    this@Format.day(padding = Padding.ZERO)
                }
            }
        })
    } catch (e: IllegalArgumentException) {
        Log.e("CLJR", "From toDateTimeRuString ${e.message}")
        return null
    } catch (e: Exception) {
        Log.e("CLJR", "From toDateTimeRuString ${e.message}")
        return null
    }
}

fun LocalDateTime.formatRu(format: FormatDate = FormatDate.DateTime): String {
   return this.format(when (format) {
        FormatDate.DateTime -> {
            LocalDateTime.Format {
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
        }

        FormatDate.Date -> LocalDateTime.Format {
            this@Format.day(padding = Padding.ZERO)
            char('.')
            monthNumber(padding = Padding.ZERO)
            char('.')
            year()
        }

        FormatDate.Time -> {
            LocalDateTime.Format {
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
    }

       FormatDate.Month -> {
           LocalDateTime.Format {
               monthNumber(padding = Padding.ZERO)
           }
       }
       FormatDate.Day -> LocalDateTime.Format {
           this@Format.day(padding = Padding.ZERO)
       }
   })

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
fun Long.toLocalDateTimeRuString(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    formatDate: FormatDate = FormatDate.DateTime
): String? {
    return this.toLocalDateTimeRu(timeZone = timeZone)?.toDateTimeRuString(formatDate)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toDateTimeRuString(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    formatDate: FormatDate = FormatDate.DateTime
): String? {
    return this.toLocalDateTime(timeZone).toDateTimeRuString(formatDate)
}
//fun Int.toTimeRu():String?{
//   if (this.th)
//}