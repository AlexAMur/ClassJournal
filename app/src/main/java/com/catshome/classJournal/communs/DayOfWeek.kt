package com.catshome.classJournal.communs

enum class DayOfWeek(val shortName: String) {
        MONDAY("Пн"), TUESDAY("Вт"), WEDNESDAY("Ср"), THURSDAY("Чт"),
        FRIDAY("Пт"), SATURDAY("Сб"), SUNDAY("Вс");

      //  fun isWeekend() = this == SATURDAY || this == SUNDAY
    }
