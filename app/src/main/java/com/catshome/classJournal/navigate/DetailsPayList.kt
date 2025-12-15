package com.catshome.classJournal.navigate

import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.Serializable

@Serializable
data class DetailsPayList(
    var isShowSnackBar: Boolean =false,
    val Message: String? = null,
    val childId: String? = null,
    val childFIO: String? = null,
    val selectOption: Int = 0,
    val beginDate: String? = null,
    val endDate: String? = null
)
