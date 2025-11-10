package com.catshome.classJournal.screens.child

import com.catshome.classJournal.domain.Child.ChildWithGroups

data class  ChildListState(
    var uidDelete: String = "",
    var snackBarShow: Boolean = false,
    var reloadScreen: Boolean = false,
    var snackMessage: String ="",
    var snackActionLabel: String ="",
    var showFAB : Boolean= false,
    var onDismissed: (()->Unit)? = null,
    var onActionPerformed: (()->Unit)? = null,
    var item: MutableMap<String ,List<ChildItem>> = mutableMapOf()
)

data class ChildItem (
    val isOptionsRevealed: Boolean = false,
    val child: ChildWithGroups
)
