package com.catshome.classJournal.navigate

import kotlinx.serialization.Serializable

@Serializable
data class DetailsPayResult(
    val message: String? = null,
    val isShowSnackBar: Boolean =false

)
