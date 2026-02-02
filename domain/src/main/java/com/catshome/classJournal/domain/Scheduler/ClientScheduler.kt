package com.catshome.classJournal.domain.Scheduler

data class ClientScheduler(
    val uidChild: String? = null,
    val uidGroup: String? = null,
    val name: String,
    val isChecked: Boolean = false,
)
