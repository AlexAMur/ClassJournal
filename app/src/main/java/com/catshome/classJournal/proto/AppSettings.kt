package com.catshome.classJournal.proto

import com.catshome.classJournal.ClassJournalStyle
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    var colorMode: ColorMode = ColorMode.Dark,
    var style: ClassJournalStyle = ClassJournalStyle.Blue,
    var periodToPay: optionPeriod = optionPeriod.MONTH,
    var idSelectChildToPay: String? = null,
    var beginDateToPay: String? = null,
    var endDateToPay: String? = null,
    var sortPay: SortEnum = SortEnum.Date,
    var periodToVisit: optionPeriod = optionPeriod.MONTH,
    var idSelectChildToVisit: String? = null,
    var beginDateToVisit: String? = null,
    var endDateToVisit: String? = null,
    var sortVisit: SortEnum = SortEnum.Date,
    )
enum class ColorMode {
    Light, Dark
}
enum class optionPeriod(ruName: String){
    DAY("День"),
    MONTH("Месяц"),
    YEAR("Год"),
    PERIOD("Период"),
    ALL("Все")
}