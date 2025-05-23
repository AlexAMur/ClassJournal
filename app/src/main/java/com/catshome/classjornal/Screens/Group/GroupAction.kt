package com.catshome.classjornal.Screens.Group

import com.catshome.ClassJournal.domain.Group.Models.Group

sealed class ComposeAction {
    object NextClicked : ComposeAction()
    object Success: ComposeAction()
    object CloseScreen : ComposeAction()
}