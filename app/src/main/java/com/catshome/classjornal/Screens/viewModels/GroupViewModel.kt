package com.catshome.classjornal.Screens.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.domain.Group.Models.Group
import com.catshome.classjornal.Screens.Group.GroupAction
import com.catshome.classjornal.Screens.Group.GroupEvent
import com.catshome.classjornal.Screens.Group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//@HiltViewModel
class GroupViewModel : BaseViewModel<GroupState, GroupAction,GroupEvent>  (installSate = GroupState()) {

    override fun obtainEvent(viewEvent: GroupEvent) {
        when(viewEvent){
            is GroupEvent.ChangeName->{
                viewState =viewState.copy(nameGroup =viewEvent.nameGroup)
             }
            is GroupEvent.DeleteGroup->{
                viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            GroupEvent.ActionInvoked ->viewAction = null
            GroupEvent.NextClicked -> viewAction = GroupAction.NextClicked
        }

            }
}
