package com.catshome.ClassJournal.Screens.viewModels

import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.domain.Group.Models.Group
import com.catshome.ClassJournal.Screens.Group.ComposeAction
import com.catshome.ClassJournal.Screens.Group.NewGroupEvent
import com.catshome.ClassJournal.Screens.Group.NewGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
internal val New =-1L

@HiltViewModel
class NewGroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<NewGroupState, ComposeAction, NewGroupEvent>(installState = NewGroupState()) {
    override fun obtainEvent(viewEvent: NewGroupEvent) {
        when (viewEvent) {
            is NewGroupEvent.ChangeName -> {
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
                        viewState.nameGroup,
                        viewState.isDelete
                    )
                )
                viewAction = ComposeAction.CloseScreen

            }

            NewGroupEvent.NextClicked -> viewAction = null
        }

    }
}
