package com.catshome.classJournal.domain.communs


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant

const val DATE_FORMAT_RU = "dd.MM.yyyy"
const val DATETIME_FORMAT_RU = "dd.MM.yyyy HH:mm"
const val TIME_FORMAT = "HH:mm"


@RequiresApi(Build.VERSION_CODES.O)
fun String.toDateRu(): Date? {
    try {
        var formatter: SimpleDateFormat
        if (this.length == DATE_FORMAT_RU.length)
            formatter = SimpleDateFormat(DATE_FORMAT_RU, Locale.getDefault())
        else{
            formatter = SimpleDateFormat(DATETIME_FORMAT_RU, Locale.getDefault())
        }
        return formatter.parse(this)
    } catch (e: ParseException) {
        Log.e("CLJR", "toDateRu ${e.message.toString()}")
        this.toLocalDateTime()?.toLong()?.let {
            return Date(it)
        }
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(): LocalDateTime? {
    try {
        val formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT_RU)
        return LocalDateTime.parse(this, formatter)
    } catch (e: RuntimeException) {
        Log.e("CLJR", "Ошибка форматирование ${e.message}")
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateTimeRuString(): String {
    val formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT_RU)
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateRuString(): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_RU)
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toLong(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalTime::class)
fun Long.toLocalDateTimeRu(): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(this)
    return LocalDateTime.ofInstant(instant.toJavaInstant(), ZoneId.systemDefault())
}

fun Long.toDateStringRU(): String {
    return convertMillisToDate(this)
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(DATE_FORMAT_RU, Locale.getDefault())
    return formatter.format(Date(millis))
}
