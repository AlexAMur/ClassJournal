package com.catshome.classJournal.domain.PayList

data class PayItem(val uidPay: String = "",
                   val uidChild: String = "",
                   val name: String = "",
                   val surname: String = "",
                   val datePay: String = "",
                   val payment: String = "")
