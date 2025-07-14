package com.catshome.classJournal.screens.viewModels

import android.util.Log
import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.group.ComposeAction
import com.catshome.classJournal.screens.group.NewGroupEvent
import com.catshome.classJournal.screens.group.NewGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewGroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<NewGroupState, ComposeAction, NewGroupEvent>(installState = NewGroupState()) {
        init {
            Log.e("CLJR", "Viewmodel "+this)

        }

    override fun obtainEvent(viewEvent: NewGroupEvent) {
        when (viewEvent) {
           is NewGroupEvent.OpenGroup -> {

                viewState = viewState.copy(groupInteractor.getGroupByID(viewEvent.id))
            }

            is NewGroupEvent.ChangeName -> {
                if (viewState.isError && !viewState.nameGroup.isEmpty())
                    viewState.isError = false
                viewState = viewState.copy(nameGroup = viewEvent.nameGroup)
            }

            is NewGroupEvent.DeleteGroup -> {
                viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            NewGroupEvent.ActionInvoked -> viewAction = null

            NewGroupEvent.SaveClicked -> {
                    groupInteractor.saveGroupUseCase(
                        Group(
                            viewState.uid,
                            viewState.nameGroup.trim(),
                            viewState.isDelete
                        )
                    )
                    viewAction = ComposeAction.CloseScreen
            }
            NewGroupEvent.NextClicked -> viewAction = null
        }
    }
}
