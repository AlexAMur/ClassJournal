package com.catshome.classjornal.Screens.Group

import android.view.View
import androidx.compose.ui.graphics.vector.Group
import com.catshome.ClassJournal.domain.Group.Models.Group


data class GroupState (
    val uid : Int =-1,
    val isDelete: Boolean =false,
    val nameGroup:String  = ""
)