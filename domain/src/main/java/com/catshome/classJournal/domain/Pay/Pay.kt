 package com.catshome.classJournal.domain.Pay

import kotlinx.serialization.Serializable

@Serializable
data class Pay(
    val uidPay: String= "",
    val uidChild: String= "",
    val name: String= "",
    val surName: String= "",
    val datePay: String= "",
    val payment: Int = 0
)


