package com.catshome.ClassJournal.Screens.viewModels

import androidx.lifecycle.viewModelScope
import com.catshome.ClassJournal.Screens.Group.GroupAction
import com.catshome.ClassJournal.Screens.Group.GroupEvent
import com.catshome.ClassJournal.Screens.Group.GroupState
import com.catshome.ClassJournal.domain.Group.GroupInteractor
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
            is GroupEvent.ReloadScreen -> {
                reloadScreen()
            }

            is GroupEvent.NewClicked -> {
                viewAction = GroupAction.OpenGroup(0)
            }

            is GroupEvent.DeleteClicked -> {
                groupInteractor.deleteGroupUseCase(groupInteractor.getGroupByID(viewEvent.uid))
              //  obtainEvent(GroupEvent.ReloadScreen)
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
                viewState = viewState.copy(viewState.indexButton, group)
            }
        }
    }
}
