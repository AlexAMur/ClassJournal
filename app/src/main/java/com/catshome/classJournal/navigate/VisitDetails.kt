package com.catshome.classJournal.navigate

import com.catshome.classJournal.domain.Visit.Visit
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class VisitDetails(
    val uid: String = "",
    val uidChild: String? = null,
    val fio: String? = null,
    val date: String? = null,
    val price: Int? = null
)

fun VisitDetails.mapToVisit(): Visit {
    return Visit(
        uid = this.uid,
        dayOfWeek = null,
        startLesson = 0,
        uidChild = this.uidChild,
        fio = this.fio.toString(),
        groupName = null,
        data = this.date,
        price = this.price,
        priceScreen = this.price.toString(),
        check = false,
        isDelete = false
    )
}

