package com.catshome.classJournal.navigate

import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.Serializable

@Serializable
data class DetailsPayList(
    var isShowSnackBar: Boolean =false,
    val Message: String =""
)
