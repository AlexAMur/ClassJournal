package com.catshome.ClassJournal.Screens.viewModels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        //TODO написать обработчик для кнопки add
        viewModelScope.launch(Dispatchers.Default) {
            val listGroup = groupInteractor.getGroupUseCase(false).collect { group ->
                viewState = viewState.copy(group)

            }
        }
    }


    override fun obtainEvent(viewEvent: GroupEvent) {
        when (viewEvent) {
            is GroupEvent.NewClicked -> {
                // viewAction= GroupAction.
            }

            is GroupEvent.DeleteClicked -> {
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
                viewAction = GroupAction.OpenGroup(New)

            }

            // NewGroupEvent.NextClicked -> viewAction = ComposeAction.NextClicked
        }

    }
}
