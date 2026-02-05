package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class SaveLesson(
    val isShowSnackBar: Boolean,
    val message: String
)
