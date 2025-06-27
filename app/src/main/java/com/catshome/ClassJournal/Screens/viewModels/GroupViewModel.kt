package com.catshome.ClassJournal.Screens.viewModels

import android.provider.ContactsContract.Profile
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.Screens.Group.ComposeAction
import com.catshome.ClassJournal.Screens.Group.GroupAction
import com.catshome.ClassJournal.Screens.Group.GroupEvent
import com.catshome.ClassJournal.Screens.Group.NewGroupEvent
import com.catshome.ClassJournal.Screens.Group.GroupState
import com.catshome.ClassJournal.Screens.Group.NewGroupState
import com.catshome.ClassJournal.domain.Group.Models.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(val groupInteractor: GroupInteractor) :
    BaseViewModel<GroupState, GroupAction, GroupEvent>(installState = GroupState()) {

    init {
            reloadScreen()
    }


    override fun obtainEvent(viewEvent: GroupEvent) {
        when (viewEvent) {
            is GroupEvent.ReloadScreen -> {
                 reloadScreen()
            }

            is GroupEvent.NewClicked -> {
                viewAction = GroupAction.OpenGroup(0)
            }

            is GroupEvent.DeleteClicked -> {
                GroupAction.DeleteGroup(viewEvent.uid)
                // viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            //  NewGroupEvent.ActionInvoked -> viewAction = null
            is GroupEvent.UpdateGroupClicked -> {
//                groupInteractor.saveGroupUseCase(
//                    Group(
//                        viewState.uid,
//                        viewState.nameGroup,
//                        viewState.isDelete
//                    )
//                )
                //TODO select ID_Group to
                viewAction = GroupAction.OpenGroup(0)

            }

            // NewGroupEvent.NextClicked -> viewAction = ComposeAction.NextClicked
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
