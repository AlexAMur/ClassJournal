package com.catshome.classjornal.Screens.Group

import android.view.View
import androidx.compose.ui.graphics.vector.Group
import com.catshome.ClassJournal.domain.Group.Models.Group


sealed class GroupState {
    val isEdit =false
    val group  = Group(name = "")
}