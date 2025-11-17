package com.catshome.classJournal.domain.communs



import android.os.Build

import android.util.Log
import androidx.annotation.RequiresApi

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year.now
import java.time.format.DateTimeFormatter

import java.util.Date
import java.util.Locale
const val DATE_FORMAT_RU = "dd.MM.yyyy"
@RequiresApi(Build.VERSION_CODES.O)
fun String.toDateRu(): Date{
        val formatter =    SimpleDateFormat(DATE_FORMAT_RU, Locale.getDefault())
        try {
            return formatter.parse(this)
        }catch (e: ParseException){
            Log.e("CLJR", e.message.toString())
        }

    return Date()
}
//fun Long.toDateStringRu(): String{
//    val formatter =    SimpleDateFormat(DATE_FORMAT_RU, Locale.getDefault())
//    formatter.parse(this).time
//}

fun Long.toDateStringRU(): String {

 return   convertMillisToDate(this)
}
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
