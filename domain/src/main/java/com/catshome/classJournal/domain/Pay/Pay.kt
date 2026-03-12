 package com.catshome.classJournal.domain.Pay

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

 @Serializable
data class Pay(
    val uidPay: String= "",
    val uidChild: String= "",
    val name: String= "",
    val surName: String= "",
    val datePay: String= "",
    val payment: Int = 0
)

