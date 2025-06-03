package com.catshome.classjornal.Screens.viewModels

import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.domain.Group.Models.Group
import com.catshome.classjornal.Screens.Group.ComposeAction
import com.catshome.classjornal.Screens.Group.GroupEvent
import com.catshome.classjornal.Screens.Group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<GroupState, ComposeAction, GroupEvent>(installSate = GroupState()) {
    override fun obtainEvent(viewEvent: GroupEvent) {
        when (viewEvent) {
            is GroupEvent.ChangeName -> {
                viewState = viewState.copy(nameGroup = viewEvent.nameGroup)
            }

            is GroupEvent.DeleteGroup -> {
                viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            GroupEvent.ActionInvoked -> viewAction = null
            GroupEvent.SaveClicked -> {
                groupInteractor.saveGroupUseCase(
                    Group(
                        viewState.uid,
                        viewState.nameGroup,
                        viewState.isDelete
                    )
                )
                viewAction = ComposeAction.CloseScreen

            }

            GroupEvent.NextClicked -> viewAction = ComposeAction.NextClicked
        }

    }
}
