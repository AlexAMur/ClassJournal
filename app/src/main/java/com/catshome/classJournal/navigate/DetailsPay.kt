package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class DetailsPay(
    var isShowSnackBar: Boolean =false,
    val Message: String? = null
)