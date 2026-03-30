package com.catshome.classJournal.domain.communs

enum class DayOfWeek(val shortName: String,val nameRu: String) {
        MONDAY("Пн", nameRu = "Понедельник"),
    TUESDAY("Вт", nameRu = "Вторник"),
    WEDNESDAY("Ср", nameRu = "Среда"),
    THURSDAY("Чт", nameRu = "Четверг"),
        FRIDAY("Пт", nameRu = "Пятница"),
    SATURDAY("Сб", nameRu = "Суббота"),
    SUNDAY("Вс", nameRu = "Воскресенье");

      //  fun isWeekend() = this == SATURDAY || this == SUNDAY
    }