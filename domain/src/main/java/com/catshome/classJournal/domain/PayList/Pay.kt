package com.catshome.classJournal.domain.PayList

data class Pay(
    val uidPay: String = "",
    val uidChild: String = "",
    val nameSurname: String = "",
    val datePay: String = "",
    val payment: Int = 0
)