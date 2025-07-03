package com.catshome.classJournal.screens.viewModels

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.screens.group.GroupAction
import com.catshome.classJournal.screens.group.GroupEvent
import com.catshome.classJournal.screens.group.GroupState
import com.catshome.classJournal.domain.Group.GroupInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(val groupInteractor: GroupInteractor) :
    BaseViewModel<GroupState, GroupAction, GroupEvent>(installState = GroupState()) {

    init { reloadScreen() }

    override fun obtainEvent(viewEvent: GroupEvent) {
        when (viewEvent) {
            is GroupEvent.CancelDelete->{
                viewState.uidDelete =-1
                obtainEvent(GroupEvent.ReloadScreen)
            }
            is GroupEvent.RequestDelete->{
                viewState.uidDelete=viewEvent.uid
                viewAction= GroupAction.RequestDelete
            }
            is GroupEvent.ReloadScreen -> {
                reloadScreen()
            }

            is GroupEvent.NewClicked -> {
                viewAction = GroupAction.OpenGroup(0)
            }

            is GroupEvent.DeleteClicked -> {

                groupInteractor.deleteGroupUseCase(groupInteractor.getGroupByID(viewEvent.uid))
                viewState =viewState.copy()
                obtainEvent(GroupEvent.ReloadScreen)
                //viewAction = GroupAction.DeleteGroup(viewEvent.uid)
            }
            is GroupEvent.UpdateGroupClicked -> {

                groupInteractor.saveGroupUseCase(viewEvent.group)

            }
            is GroupEvent.UndoDeleteClicked -> {
                groupInteractor.unDeleteUseCase(
                        groupInteractor.getGroupByID(uid = viewEvent.uid)
                )
                //obtainEvent(GroupEvent.ReloadScreen)
            }
        }
    }

    private fun reloadScreen() {
        viewModelScope.launch(Dispatchers.Default) {
            groupInteractor.getGroupUseCase(false).collect { group ->
                viewState = viewState.copy(viewState.uidDelete, group)
            }
        }
    }
}
