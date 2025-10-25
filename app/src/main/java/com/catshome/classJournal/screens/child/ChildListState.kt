package com.catshome.classJournal.screens.child

import androidx.compose.material3.SnackbarHostState
import com.catshome.classJournal.domain.Child.ChildWithGroups

data class  ChildListState(
    var uidDelete: String = "",
    var snackBarShow: Boolean = false,
    var snackMessage: String ="",
    var snackActionLabel: String ="",
    var onDismissed: (()->Unit)? = null,
    var onActionPerformed: (()->Unit)? = null,
    var item: MutableMap<String ,List<ChildItem>> = mutableMapOf()
)

data class ChildItem (
    val isOptionsRevealed: Boolean = false,
    val child: ChildWithGroups
)
