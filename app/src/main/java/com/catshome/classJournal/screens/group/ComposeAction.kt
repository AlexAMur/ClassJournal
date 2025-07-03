package com.catshome.classJournal.screens.group

sealed class ComposeAction {
  //  object NextClicked : ComposeAction()
    object Success: ComposeAction()
    object New: ComposeAction()
   // object Delete: ComposeAction()
    object CloseScreen : ComposeAction()
}