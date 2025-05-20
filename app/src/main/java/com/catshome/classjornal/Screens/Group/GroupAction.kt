package com.catshome.classjornal.Screens.Group

import com.catshome.ClassJournal.domain.Group.Models.Group

sealed class GroupAction {
    object NextClicked : GroupAction()
}