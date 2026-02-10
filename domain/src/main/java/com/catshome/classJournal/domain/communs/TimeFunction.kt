package com.catshome.classJournal.domain.communs

fun Long.toTimeString(is24Hour: Boolean = true): String{
    val MIN =60L
    var s = ""
    val HOUR=24L
    if(this/ MIN < 10L)
        if (this/ MIN == 0L)
            s= "00"
        else  s = "0${this/MIN}"
    else s = "${this/MIN}"
    s += if(this%MIN < 10)
        if (this%MIN !=0L)
            ":0${(this % MIN)}"
        else
            ":00"
    else
        ":${this%MIN}"
    return s
}
fun Int.toTimeString(is24Hour: Boolean = true): String{
    val MIN =60
    var s = ""
    val HOUR=24
    if(this/ MIN < 10)
        if (this/ MIN == 0)
            s= "00"
        else  s = "0${this/MIN}"
    else s = "${this/MIN}"
    if(this%MIN < 10)
        if (this%MIN !=0)
            s+=":0${(this % MIN)}"
        else
            s+=":00"
    else
        s+=":${this%MIN}"
    return s
}